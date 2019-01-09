/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import graph.AdjacencyMatrixGraph;
import graph.EdgeAsDoubleGraphAlgorithms;
import java.util.LinkedList;
import graphbase.Graph;
import graphbase.GraphAlgorithms;

/**
 *
 * @author Raúl Correia <1090657@isep.ipp.pt>
 */
public class ControloDoJogo {

    private AdjacencyMatrixGraph<Local, Double> grafo_locais_estradas;
    private Graph<Personagem, Boolean> grafo_personagens_aliancas;

    private final static String LOCAIS_S = "locais_S.txt";
    private final static String LOCAIS_M = "locais_M.txt";
    private final static String LOCAIS_L = "locais_L.txt";
    private final static String LOCAIS_XL = "locais_XL.txt";
    private final static String LOCAIS_TESTE = "locais_TEST.txt";

    private final static String PERSONAGEM_S = "pers_S.txt";
    private final static String PERSONAGEM_M = "pers_M.txt";
    private final static String PERSONAGEM_L = "pers_L.txt";
    private final static String PERSONAGEM_XL = "pers_XL.txt";
    private final static String PERSONAGEM_TESTE = "pers_TEST.txt";

    public final static String FICH_S = "S";
    public final static String FICH_M = "M";
    public final static String FICH_L = "L";
    public final static String FICH_XL = "XL";
    public final static String FICH_TESTE = "TEST";

    ControloDoJogo() {
        grafo_locais_estradas = new AdjacencyMatrixGraph<>();
        grafo_personagens_aliancas = new Graph(false);

    }

    //===================================1 A====================================
    /**
     * Método para ler os locais de um ficheiro de texto para o grafo de locais
     *
     * @param nomeFicheiro Nome do ficheiro
     */
    public void lerLocais(String nomeFicheiro) {
        Ficheiro f = new Ficheiro();
        f.lerLocais(nomeFicheiro, this); //Total: O(n*Vlocal^2)

    }

    //===================================1 B====================================
    //Apresentar o caminho com	menor	dificuldade (considerando apenas a dificuldade	das	estradas) entre dois locais
    /**
     * Método que apresenta o caminho com a estradas com menor dificuldade
     * Baseado no método de Dijkstra
     *
     * @param source De onde o caminho começa
     * @param target Para onde o caminho acaba
     * @return double com o tamanho do caminho
     */
    public LinkedList<Local> caminhoComMenorDificuldade(Local source, Local target) {
        LinkedList<Local> path = new LinkedList<>();                            //O(1)
        EdgeAsDoubleGraphAlgorithms.shortestPath(grafo_locais_estradas, source, target, path);//O(V^2)
        return path;                                                            //O(1)
    }                                                                           //Total:O(V^2)

    //===================================1 C====================================
    //Determinar qual a	aliança	mais forte, retornando	a força	e as personagens dessa aliança
    /**
     * Método que verifica se uma dada personagem pode conquistar um dado local,
     * percorrendo o caminho com menor esforço
     *
     * @param pers Personagem a Conquistar
     * @param source Local de onde começa
     * @param target Local a conquistar
     * @param grafo_locais_alternativo Grafo de locais alternativo, Dependência
     * para satisfazer o 3F para poder conquistar com aliados, caso o grafo
     * passado por parâmetro seja null, utiliza o grafo locais por omissão
     * @return Se uma conquista é valida, com dificuldade e caminho
     */
    public Conquista verificarConquista(Personagem pers, Local source, Local target, AdjacencyMatrixGraph<Local, Double> grafo_locais_alternativo) {
        AdjacencyMatrixGraph<Local, Double> grafo_locais_a_utilizar = grafo_locais_estradas;
        if (grafo_locais_alternativo != null) {                                                             //O(1)
            grafo_locais_a_utilizar = grafo_locais_alternativo;                                             //O(1)
        }
        if (!grafo_personagens_aliancas.validVertex(pers) || !grafo_locais_a_utilizar.checkVertex(target)) {//O(1)
            return new Conquista(false, -1, null);                                                          //O(1)
        }
        if (target.getDono() != null && target.getDono().equals(pers)) {                                    //O(1)
            return new Conquista(false, -1, null);                                                          //O(1)
        }
        if (source.getDono() != null && !source.getDono().equals(pers)) {
            return new Conquista(false, -1, null);
        }
        final double SEM_CAMINHO = -1;                                                                      //O(1)
        LinkedList<Local> path = new LinkedList<>();                                                        //O(1)

        double dificuldade = graph.AlgoritmosJogo.shortestPathConquista(grafo_locais_a_utilizar, source, target, path, pers); //O(V^2)
        if (dificuldade != SEM_CAMINHO) {                                                                   //O(1)
            if (path.peekFirst() == source) {                                                               //O(1)
                path.removeFirst();                                                                         //O(1)
            }
            if (path.peekLast() == target) {                                                                //O(1)
                path.removeLast();                                                                          //O(1)
            }
            boolean consegue_conquistar = false;                                                            //O(1)
            if (pers.getForca() > dificuldade) {                                                            //O(1)
                consegue_conquistar = true;                                                                 //O(1)
            }
            Conquista cq = new Conquista(consegue_conquistar, dificuldade, path);                           //O(1)
            return cq;                                                                                      //O(1)
        }
        return new Conquista(false, -1, path);                                                              //O(1)
    }                                                                                                       //Total: O(V^2)

    //===================================2 A====================================
    //Construir	o grafo a partir de um ficheiro de texto com a informação estruturada
    /**
     * Método que lê o ficheiro de Personagens e as suas Aliancas para o
     * grafo_personagens_aliancas
     *
     * @param nomeFicheiro - String com o nome do Ficheiro a ler
     */
    public void lerAlianca(String nomeFicheiro) {
        Ficheiro f = new Ficheiro();
        f.lerPersonagensAliancas(nomeFicheiro, this); //Total O(n*Vpers)
    }

    //===================================2 B====================================
    /**
     * Método que devolve todos os aliados diretos de uma dada personagem
     *
     * @param source Personagem qual se quer os aliados
     * @return Iterable de Personagens aliadas
     */
    public Iterable<Personagem> devolverTodosAliados(Personagem source) {
        if (!grafo_personagens_aliancas.validVertex(source)) {  //O(1)
            return null;
        }
        return grafo_personagens_aliancas.adjVertices(source); //O(1)
    }                                                          //Total: O(1)

    ////===================================2 C====================================
    //Determinar qual a aliança mais forte, retornando a força e as personagens dessa aliança
    /**
     * Método que percorre todos os vértices de Personagens e os seus Adjacentes
     * E determina qual a alianca mais forte, independentemente de ser Pública
     * ou Privada
     *
     * @return AliancaMaisForte
     */
    public AliancaMaisForte determinarAliancaMaisForte() {
        Personagem p_a = null;                                                  //O(1)
        Personagem p_b = null;                                                  //O(1)

        double forca_alianca = -1;                                              //O(1)
        for (Personagem p : grafo_personagens_aliancas.vertices()) {            //O(V)
            for (Personagem pAdj : grafo_personagens_aliancas.adjVertices(p)) { //O(V)
                if (forca_alianca == -1) {                                      //O(1)
                    p_a = p;                                                    //O(1)
                    p_b = pAdj;                                                 //O(1)
                    forca_alianca = (p.getForca() + pAdj.getForca()) * grafo_personagens_aliancas.getEdge(p, pAdj).getWeight();//O(1)
                } else {
                    double outra_forca = (p.getForca() + pAdj.getForca()) * grafo_personagens_aliancas.getEdge(p, pAdj).getWeight(); //O(1)
                    if (outra_forca > forca_alianca) {                          //O(1)
                        p_a = p;                                                //O(1)
                        p_b = pAdj;                                             //O(1)
                        forca_alianca = outra_forca;                            //O(1)
                    }

                }
            }
        }                                                                       //Total bloco : O(V^2)
        if (p_a != null && p_b != null) {
            AliancaMaisForte amf = new AliancaMaisForte(forca_alianca, p_a, p_b);//O(1)
            return amf;                                                         //O(1)

        }
        return null;                                                            //O(1)
    }                                                                           //Total O(V^2)

    //===================================2 D====================================
    //Realizar	uma nova aliança entre	uma personagem A e uma personagem B.
    /**
     * Método que recebe como parâmetro duas personagens, o tipo de alianca e o
     * fator de compatibilidade Efetua uma nova alianca entre A e B Caso B
     * esteja contida na rede de alianças de A, o fator de compatibilidade é
     * igual à média da comp. da rede do menor caminho de alianca. Senão é usado
     * o fator_compatibilidade passado por parâmetr ----PONTO 1---- Após
     * conversa em aula com o professor, foi removido o random entre uma nova
     * aliança A e B e como novo critéiro, adicionou-se um parâmetro passado na
     * função com o valor da compatibilidade para facilitar os testes
     *
     *
     * @param p_source Personagem A
     * @param p_target Personagem B
     * @param tipoalianca Tipo de Alianca
     * @param fator_compatibilidade Fator de Compatibilidade
     * @return True or False se a alianca foi adicionada
     */
    public boolean novaAlianca(Personagem p_source, Personagem p_target, boolean tipoalianca, double fator_compatibilidade) {
        final double SEM_CAMINHO = -1;
        if (!grafo_personagens_aliancas.validVertex(p_source) || !grafo_personagens_aliancas.validVertex(p_target)) {   //O(1)
            return false;                                                                                               //O(1)
        }

        LinkedList<Personagem> path = new LinkedList<>();                                                               //O(1)
        double dist = 0;                                                                                                //O(1)

        //Vamos ver o caminho de p_source para p_target
        Graph<Personagem, Boolean> grafo_aliancas_publicas_sem_peso = gerarGrafoAliancasApenasPublicasSemPeso();        //O(V^2)
        dist = graphbase.GraphAlgorithms.shortestPath(grafo_aliancas_publicas_sem_peso, p_source, p_target, path);      //O(V^2)

        if (dist == SEM_CAMINHO) {                                                                                      //O(1)
            return adicionarAlianca(p_source, p_target, tipoalianca, fator_compatibilidade);                            //O(1)

        } else {
            int numRamos = path.size();                                                                                  //O(1)
            //Se o número de Personagens é maior do que 1, então é n-1 ramos
            if (numRamos > 1) {                                                                                          //O(1)
                numRamos--;                                                                                              //O(1)
            }
            double fator_comp = 0;                                                                                      //O(1)
            Personagem a = null;                                                                                        //O(1)
            Personagem b = null;                                                                                        //O(1)
            while (!path.isEmpty()) {                                                                                   //O(V)
                if (a == null && b == null) {                                                                           //O(1)
                    a = path.pop();                                                                                     //O(1)
                    b = path.pop();                                                                                     //O(1)
                    fator_comp = grafo_personagens_aliancas.getEdge(a, b).getWeight();                                  //O(1)
                } else {
                    a = b;                                                                                              //O(1)
                    b = path.pop();                                                                                     //O(1)
                    fator_comp += grafo_personagens_aliancas.getEdge(a, b).getWeight();                                 //O(1)
                }
            }
            double mediaComp = fator_comp / numRamos;                                                                    //O(1)
            grafo_personagens_aliancas.insertEdge(p_source, p_target, tipoalianca, mediaComp);                          //O(1)
            return true;                                                                                                //O(1)
        }
    }                                                                                                                   //Total:O(V^2)

    //===================================2 E====================================
    //Criar um novo grafo representando todas as novas alianças que podem ser realizadas entre todas as personagens
    //caso todas as alianças existentes fossem públicas
    /**
     * Método que dá todas as novas possíveis alianças entre personagens com os
     * respetivos novos fatores de compatibilidade
     *
     * @return Grafo com as possíveis novas alianças
     */
    public Graph<Personagem, Boolean> possiveisNovasAliancas() {

        Graph<Personagem, Boolean> ng = (Graph<Personagem, Boolean>) grafo_personagens_aliancas.clone();
        for (Personagem p_k : grafo_personagens_aliancas.vertices()) {          //O(v)
            for (Personagem p_i : grafo_personagens_aliancas.vertices()) {      //O(v)
                if (!p_i.equals(p_k) && ng.getEdge(p_i, p_k) != null) {
                    for (Personagem p_j : grafo_personagens_aliancas.vertices()) {//O(v)
                        if (!p_i.equals(p_j) && !p_j.equals(p_k) && ng.getEdge(p_k, p_j) == null) {
                            if (ng.getEdge(p_i, p_j) != null) {                   //O(1)
                                double ed1 = ng.getEdge(p_k, p_i).getWeight();    //O(1)
                                double ed2 = ng.getEdge(p_i, p_j).getWeight();    //O(1)
                                double media = (ed1 + ed2) / 2;                   //O(1)
                                ng.insertEdge(p_k, p_j, Boolean.TRUE, media);     //O(1)
                            }
                        }
                    }
                }
            }
        }
        return ng;                                                              //O(1)
    }                                                                           //O(V) * O(V) * O(V)
    //===================================3F====================================

    /*Verificar	se uma personagem pode conquistar, junto com um dos seus aliados, um determinado local	X 
    (assuma que	o dono	de X, caso exista, não usa as suas alianças), devolvendo qual o aliado,	assim como o
valor necessário e a lista mínima de locais intermédios	a conquistar,caso seja necessário.De notar que o
aliado não pode ser dono de X nem de nenhum dos locais intermédios.*/
    /**
     * Método que verifica se uma personagem pode conquistar um dado local com a
     * ajuda dos seus aliados
     *
     * @param pOrig Personagem a conquistar o local
     * @param target Local a ser conquistado
     * @return
     */
    public ConquistaComAliado conquistarComAliados(Personagem pOrig, Local source, Local target) {
        final double INVALIDO = -1;
        if (!grafo_personagens_aliancas.validVertex(pOrig) || !grafo_locais_estradas.checkVertex(target)) { //O(v)
            return null;
        }
        if (target.getDono() != null && target.getDono().equals(pOrig)) {       //O(1)
            return new ConquistaComAliado(false, -1, null, null);                //O(1)
        }
        if (source.getDono() != null && !source.getDono().equals(pOrig)) {      //O(1)
            return new ConquistaComAliado(false, -1, null, null);               //O(1)
        }

        int forca_antiga = pOrig.getForca();                                    //O(1)
        for (Personagem aliado : grafo_personagens_aliancas.adjVertices(pOrig)) {                                   //O(V)
            AdjacencyMatrixGraph<Local, Double> grafo_sem_locais_aliados = gerarGrafoSemLocaisAliados(pOrig, aliado);//O(V^2)
            pOrig.setForca(forca_antiga);                                                                           //O(1)
            pOrig.setForca(determinarForcaAlianca(pOrig, aliado));                                                  //O(1)
            Conquista cq = verificarConquista(pOrig, source, target, grafo_sem_locais_aliados);                       //O(V_locais_^2)
            if (cq.consegueConquistar()) {                                                                          //O(1)
                pOrig.setForca(forca_antiga);                                                                       //O(1)
                ConquistaComAliado cqal = new ConquistaComAliado(cq, aliado);                                       //O(1)
                return cqal;                                                                                        //O(1)
            }
        }
        pOrig.setForca(forca_antiga);                                                                               //O(1)

        return new ConquistaComAliado(false, INVALIDO, new LinkedList<Local>(), null);                              //O(1)

    }                                                                                                               //O(Vpersonagens) * (O(V^2 Locais) + O(V^2 Locais))

    /**
     * Método para adicionar um local ao grafo_locais_estradas;
     *
     * @param nomeLocal Nome do Local
     * @param dificuldade Dificuldade do Local
     * @param p Personagem associada ao local (pode ser null)
     * @return True or False
     */
    public boolean adicionarLocal(String nomeLocal, int dificuldade, Personagem p) {
        Local l = new Local(nomeLocal, dificuldade, p);
        return grafo_locais_estradas.insertVertex(l); //O(v)
    }

    /**
     * Método para adicionar um local ao grafo_locais_estradas
     *
     * @param local Local
     * @return True or False
     */
    public boolean adicionarLocal(Local local) {
        return grafo_locais_estradas.insertVertex(local); //O(v^2)

    }

    /**
     * Método para adicionarEstrada ao grafo_locais_estradas
     *
     * @param l_a Local a
     * @param l_b Local b
     * @param custo_estrada Custo da estrada
     * @return
     */
    public boolean adicionarEstrada(Local l_a, Local l_b, double custo_estrada) {
        if (!grafo_locais_estradas.checkVertex(l_a) || !grafo_locais_estradas.checkVertex(l_b)) { //O(Vlocal * Vlocal)
            return false;
        }
        if (grafo_locais_estradas.getEdge(l_a, l_b) == null) {                  //O(1)
            return grafo_locais_estradas.insertEdge(l_a, l_b, custo_estrada);   //O(1)
        }
        return false;
    }

    /**
     * Método para adicionar uma personagem nova ao grafo_personagens_aliancas
     *
     * @param p Personagem a adicionar
     * @return true or false
     */
    public boolean adicionarPersonagem(Personagem p) {
        if (!grafo_personagens_aliancas.validVertex(p)) {       //O(1)
            return grafo_personagens_aliancas.insertVertex(p);  //O(1)
        }
        return false;
    }                                                           //O(1)

    /**
     * Método que serve para adicionar uma alianca durante a leitura do ficheiro
     *
     * @param p_a Personagem a
     * @param p_b Personagem b
     * @param tipoAlianca Tipo de Alianca (publica ou privada)
     * @param fator_comp fator de compatibilidade
     * @return true or false
     */
    public boolean adicionarAlianca(Personagem p_a, Personagem p_b, boolean tipoAlianca, double fator_comp) {
        if (!grafo_personagens_aliancas.validVertex(p_a) || !grafo_personagens_aliancas.validVertex(p_b)) { // O(1) + O(1)
            return false;
        }
        if (grafo_personagens_aliancas.getEdge(p_a, p_b) == null) {             //O(1)
            grafo_personagens_aliancas.insertEdge(p_a, p_b, tipoAlianca, fator_comp);//O(1)
            return true;
        } else {
            if (grafo_personagens_aliancas.getEdge(p_a, p_b).getElement() != tipoAlianca) {//O(1)
                grafo_personagens_aliancas.getEdge(p_a, p_b).setElement(tipoAlianca);//O(1)
                return true;
            }
        }
        return false;
    }                                                                           //Total O(1)

    /**
     * Método para obter o custo de uma estrada entre dois locais
     *
     * @param a Local a
     * @param b Local b
     * @return Custo da estrada
     */
    public double obterEstrada(Local a, Local b) {
        if (!grafo_locais_estradas.checkVertex(a) || !grafo_locais_estradas.checkVertex(b)) {
            return -1;
        }
        if (grafo_locais_estradas.getEdge(a, b) != null) {
            return grafo_locais_estradas.getEdge(a, b);
        }
        return -1;
    }

    /**
     * Retorna o tipo de aliança -1 se não é valido 0 se é privado 1 se é
     * pública
     *
     * @param a Personagem a
     * @param b Personagem b
     * @return -1,0,1
     */
    public int obterTipoAlianca(Personagem a, Personagem b) {
        if (!grafo_personagens_aliancas.validVertex(a) || !grafo_personagens_aliancas.validVertex(b)) {
            return -1;
        }
        if (grafo_personagens_aliancas.getEdge(a, b) != null) {
            if (grafo_personagens_aliancas.getEdge(a, b).getElement() == true) {
                return 1;
            } else {
                return 0;
            }
        }
        return -1;
    }

    /**
     * Método para obter uma personagem pelo nome
     *
     * @param nome nome da personagem
     * @return Personagem ou Null
     */
    public Personagem obterPersonagemPorNome(String nome) {
        for (Personagem p : grafo_personagens_aliancas.vertices()) {
            if (p.getNome().equalsIgnoreCase(nome)) {
                return p;
            }
        }
        return null;
    }

    /**
     * Método para obter um local pelo nome
     *
     * @param nome Nome do local
     * @return Local ou null
     */
    public Local obterLocalPorNome(String nome) {
        for (Local l : grafo_locais_estradas.vertices()) {
            if (l.getNome().equalsIgnoreCase(nome)) {
                return l;
            }
        }
        return null;
    }

    /**
     * Retorna o número de estradas
     *
     * @return
     */
    public int numEstradas() {
        return grafo_locais_estradas.numEdges();
    }

    /**
     * Retorna o número de locais
     *
     * @return
     */
    public int numLocais() {
        return grafo_locais_estradas.numVertices();
    }

    /**
     * Retorna o número de personagens
     *
     * @return
     */
    public int numPersonagens() {
        return grafo_personagens_aliancas.numVertices();
    }

    /**
     * Retorna o número de Aliancas
     *
     * @return
     */
    public int numAliancas() {
        return grafo_personagens_aliancas.numEdges();
    }

    /**
     * Retorna o número de aliados diretos
     *
     * @param source
     * @return
     */
    public int numAliadosDiretos(Personagem source) {
        if (!grafo_personagens_aliancas.validVertex(source)) {
            return -1;
        }
        Iterable<Personagem> itrbl = grafo_personagens_aliancas.adjVertices(source);
        int num = 0;
        for (Personagem p : itrbl) {
            num++;
        }
        return num;
    }

    /**
     * Método para obter o fator de compatibilidade entre duas aliancas
     *
     * @param a Personagem a
     * @param b Personagem b
     * @return -1 se não tiver ligação, outro valor se tiver ligação
     */
    public double obterFatorCompAlianca(Personagem a, Personagem b) {
        if (!grafo_personagens_aliancas.validVertex(a) || !grafo_personagens_aliancas.validVertex(b)) {
            return -1;
        }
        if (grafo_personagens_aliancas.getEdge(a, b) != null) {
            return grafo_personagens_aliancas.getEdge(a, b).getWeight();
        }
        return -1;
    }

    /**
     * Método que clona o grafo das aliancas
     *
     * @return Clone do grafo_personagens_aliancas
     */
    public Graph<Personagem, Boolean> cloneAliancas() {
        return (Graph<Personagem, Boolean>) grafo_personagens_aliancas.clone();
    }

    /**
     * Método para obter o local associado a uma personagem
     *
     * @param p Personagem a procurar o locaç
     * @return Local ou null
     */
    private Local obterLocalAssociadoAPersonagem(Personagem p) {
        if (!grafo_personagens_aliancas.validVertex(p)) {
            return null;
        }
        for (Local l : grafo_locais_estradas.vertices()) {
            if (l.getDono() != null) {
                if (l.getDono().equals(p)) {
                    return l;
                }
            }
        }
        return null;
    }

    /**
     * Método que trata da leitura de ficheiros pela a ordem devida
     *
     * @param nomeFicheiro Nome de Ficheiro prédefinido na classe
     */
    public void lerDados(String nomeFicheiro) {
        switch (nomeFicheiro) {
            case FICH_S:
                lerAlianca(PERSONAGEM_S);
                lerLocais(LOCAIS_S);
                break;
            case FICH_M:
                lerAlianca(PERSONAGEM_M);
                lerLocais(LOCAIS_M);
                break;
            case FICH_L:
                lerAlianca(PERSONAGEM_L);
                lerLocais(LOCAIS_L);
                break;
            case FICH_XL:
                lerAlianca(PERSONAGEM_XL);
                lerLocais(LOCAIS_XL);
                break;
            case FICH_TESTE:
                lerAlianca(PERSONAGEM_TESTE);
                lerLocais(LOCAIS_TESTE);
                break;
            default:
                break;
        }
    }

    /**
     * Método que retorna se duas personagens são aliadas
     *
     * @param a Personagem a
     * @param b Personagem b
     * @return True or False
     */
    public boolean saoAliados(Personagem a, Personagem b) {
        if (!grafo_personagens_aliancas.validVertex(a) || !grafo_personagens_aliancas.validVertex(b)) {
            return false;
        }
        if (grafo_personagens_aliancas.getEdge(a, b) != null) {
            return true;
        }
        return false;
    }

    /**
     * Devolve todas as personagens
     *
     * @return
     */
    public Iterable<Personagem> devolverTodasPersonagens() {
        return grafo_personagens_aliancas.vertices();
    }

    /**
     * Método que devolve todos os locais
     *
     * @return
     */
    public Iterable<Local> devolverTodosLocais() {
        return grafo_locais_estradas.vertices();
    }

    /**
     * Método que gera um grafo de alianças para as alianças apenas públicas
     *
     * @return grafo com as alianças públicas
     */
    public Graph<Personagem, Boolean> gerarGrafoAliancasPublicas() {
        Graph<Personagem, Boolean> grafo_aliancas_publicas = grafo_personagens_aliancas.clone();
        for (Personagem pOrig : grafo_personagens_aliancas.vertices()) {
            for (Personagem pAdj : grafo_personagens_aliancas.adjVertices(pOrig)) {
                if (grafo_personagens_aliancas.getEdge(pOrig, pAdj) != null && grafo_personagens_aliancas.getEdge(pOrig, pAdj).getElement() == false) {
                    grafo_aliancas_publicas.removeEdge(pOrig, pAdj);
                }
            }
        }

        return grafo_aliancas_publicas;
    }

    /**
     * Método que gera um grafo de alianças para as alianças apenas públicas,
     * sem peso
     *
     * @return grafo com as alianças todas publicas
     */
    public Graph<Personagem, Boolean> gerarGrafoAliancasApenasPublicasSemPeso() {
        Graph<Personagem, Boolean> grafo_aliancas_publicas = grafo_personagens_aliancas.clone();
        for (Personagem pOrig : grafo_personagens_aliancas.vertices()) {                    //O(V)
            for (Personagem pAdj : grafo_personagens_aliancas.adjVertices(pOrig)) {         //O(V)
                if (grafo_personagens_aliancas.getEdge(pOrig, pAdj) != null && grafo_personagens_aliancas.getEdge(pOrig, pAdj).getElement() == false) {//O(1)
                    grafo_aliancas_publicas.removeEdge(pOrig, pAdj);                        //O(1)
                } else {
                    if (grafo_personagens_aliancas.getEdge(pOrig, pAdj) != null && grafo_personagens_aliancas.getEdge(pOrig, pAdj).getElement() == true) {//O(1)
                        grafo_aliancas_publicas.getEdge(pOrig, pAdj).setWeight(1);          //O(1)
                    }

                }
            }
        }

        return grafo_aliancas_publicas;
    }                                                                           //O(V^2)

    /**
     * Método que gera um grafo de alianças para as alianças como se fossem
     * públicas, com peso 1
     *
     * @return
     */
    public Graph<Personagem, Boolean> gerarGrafoAliancasTodasPublicasSemPeso() {
        Graph<Personagem, Boolean> grafo = grafo_personagens_aliancas.clone();
        for (Personagem pOrig : grafo_personagens_aliancas.vertices()) {
            for (Personagem pAdj : grafo_personagens_aliancas.adjVertices(pOrig)) {
                if (grafo_personagens_aliancas.getEdge(pOrig, pAdj) != null) {
                    grafo.getEdge(pOrig, pAdj).setElement(Boolean.TRUE);
                    grafo.getEdge(pOrig, pAdj).setWeight(1);
                }
            }
        }

        return grafo;
    }

    /**
     * Método que retorna um grafo onde não tem os locais das personagens
     * aliadas
     *
     * @param orig Personagem A conquistar locais
     * @param aliado Aliado da personagem a quem se vai remover locais
     * @return
     */
    public AdjacencyMatrixGraph<Local, Double> gerarGrafoSemLocaisAliados(Personagem orig, Personagem aliado) {
        AdjacencyMatrixGraph<Local, Double> grafo_locais_sem_o_aliado = (AdjacencyMatrixGraph<Local, Double>) grafo_locais_estradas.clone();//O(V^2)

        for (Local loc : grafo_locais_estradas.vertices()) {                    //O(n)
            if (loc.getDono() != null) {                                        //O(1)
                if (loc.getDono().equals(aliado)) {                             //O(1)
                    grafo_locais_sem_o_aliado.removeVertex(loc);                //O(V)
                }
            }

        }
        return grafo_locais_sem_o_aliado;
    }                                                                           //O(V^2)

    /**
     * Método que determina o fator de compatibilidade entre uma rede de
     * alianças
     *
     * @param pers_a Personagem A
     * @param pers_b Personagem B
     * @param apenas_publicas True Se se considera apenas as aliancas publicas
     * ou não, false se se considera todas as aliancas como sendo publicas
     * @return Média de fator de compatibilidade de uma aliança
     */
    public double determinarFatorCompatibilidade(Personagem pers_a, Personagem pers_b, boolean apenas_publicas) {
        LinkedList<Personagem> path = new LinkedList<>();                       //O(1)

        Graph<Personagem, Boolean> grafo = null;                                //O(1)
        if (apenas_publicas) {                                                  //O(1)
            grafo = gerarGrafoAliancasApenasPublicasSemPeso();                  //O(1)
        } else {
            grafo = gerarGrafoAliancasTodasPublicasSemPeso();                   //O(1)
        }
        GraphAlgorithms.shortestPath(grafo, pers_a, pers_b, path);              //O(V^2)

        if (path.isEmpty()) {                                                   //O(1)
            return -1;
        }
        int numPers = path.size();                                              //O(1)
        //Se o número de Personagens é maior do que 1, então é n-1 ramos
        if (numPers > 1) {                                                      //O(1)
            numPers--;                                                          //O(1)
        }
        double fator_comp = 0.0;                                                //O(1)
        Personagem a = null;                                                    //O(1)
        Personagem b = null;                                                    //O(1)
        LinkedList<Personagem> clone = (LinkedList<Personagem>) path.clone();   //O(n)
        while (!clone.isEmpty()) {                                              //O(n)
            if (a == null && b == null) {                                       //O(1)
                a = clone.pop();                                                //O(1)
                b = clone.pop();                                                //O(1)
                fator_comp = grafo_personagens_aliancas.getEdge(a, b).getWeight();//O(1)
            } else {
                a = b;                                                          //O(1)
                b = clone.pop();                                                //O(1)
                fator_comp += grafo_personagens_aliancas.getEdge(a, b).getWeight();//O(1)
            }
        }
        double rt = fator_comp / numPers;                                       //O(1)
        return rt;                                                              //O(1)

    }                                                                           //O(V^2)

    /**
     * Método que determina a força de uma aliança
     *
     * @param a Personagem a
     * @param b Personagem b
     * @return a força da aliança em função do fator de compatibilidade
     */
    public int determinarForcaAlianca(Personagem a, Personagem b) {
        if (!grafo_personagens_aliancas.validVertex(a) || !grafo_personagens_aliancas.validVertex(b)) {//O(1)
            return -1;
        }
        return (int) ((a.getForca() + b.getForca()) * grafo_personagens_aliancas.getEdge(a, b).getWeight());//O(1)
    }
}
