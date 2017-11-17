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
import java.util.ArrayList;

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
        f.lerLocais(nomeFicheiro, this);

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
        LinkedList<Local> path = new LinkedList<>();
        EdgeAsDoubleGraphAlgorithms.shortestPath(grafo_locais_estradas, source, target, path);
        return path;

    }

    //===================================1 C====================================
    //Determinar qual a	aliança	mais forte, retornando	a força	e as personagens dessa aliança
    public Conquista verificarConquista(Personagem pers, Local target) {
        if (!grafo_personagens_aliancas.validVertex(pers) || !grafo_locais_estradas.checkVertex(target)) {
            LinkedList<Local> naotemcaminho = new LinkedList<>();
            return new Conquista(false, -1, naotemcaminho);
        }
        final double SEM_CAMINHO = -1;
        Local source = obterLocalAssociadoAPersonagem(pers);
        LinkedList<Local> path = new LinkedList<>();
        double dificuldade = graph.AlgoritmosJogo.shortestPathConquista(grafo_locais_estradas, source, target, path);
        if (dificuldade != SEM_CAMINHO) {
            if (path.peekFirst() == source) {
                path.removeFirst();
            }
            if (path.peekLast() == target) {
                path.removeLast();
            }
            boolean consegue_conquistar = false;
            if (pers.getForca() > dificuldade) {
                consegue_conquistar = true;
            }
            Conquista cq = new Conquista(consegue_conquistar, dificuldade, path);
            return cq;
        }
        return new Conquista(false, -1, path);
    }

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
        f.lerPersonagensAliancas(nomeFicheiro, this);
    }

    //===================================2 B====================================
    public Iterable<Personagem> devolverTodosAliados(Personagem source) {
        if (!grafo_personagens_aliancas.validVertex(source)) {
            return null;
        }
        return grafo_personagens_aliancas.adjVertices(source);
    }

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
        Personagem p_a = null;
        Personagem p_b = null;

        double forca_alianca = -1;
        for (Personagem p : grafo_personagens_aliancas.vertices()) {
            for (Personagem pAdj : grafo_personagens_aliancas.adjVertices(p)) {
                if (forca_alianca == -1) {
                    p_a = p;
                    p_b = pAdj;
                    forca_alianca = (p.getForca() + pAdj.getForca()) * grafo_personagens_aliancas.getEdge(p, pAdj).getWeight();
                } else {
                    double outra_forca = (p.getForca() + pAdj.getForca()) * grafo_personagens_aliancas.getEdge(p, pAdj).getWeight();
                    if (outra_forca > forca_alianca) {
                        p_a = p;
                        p_b = pAdj;
                        forca_alianca = outra_forca;
                    }

                }
            }
        }
        if (p_a != null && p_b != null) {
            AliancaMaisForte amf = new AliancaMaisForte(forca_alianca, p_a, p_b);
            return amf;

        }
        return null;
    }

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
     * --PONTO 2 Foi implementado o algoritmo de dijkstra para grafos não
     * pesados Devolvendo assim o menor caminho em número de arestas
     *
     * @param p_source Personagem A
     * @param p_target Personagem B
     * @param tipoalianca Tipo de Alianca
     * @param fator_compatibilidade Fator de Compatibilidade
     * @return True or False se a alianca foi adicionada
     */
    public boolean novaAlianca(Personagem p_source, Personagem p_target, boolean tipoalianca, double fator_compatibilidade) {
        final double SEM_CAMINHO = -1;
        if (!grafo_personagens_aliancas.validVertex(p_source) || !grafo_personagens_aliancas.validVertex(p_target)) {
            return false;
        }

        LinkedList<Personagem> path = new LinkedList<>();
        Graph<Personagem, Boolean> grafo_aliancas_publicas = gerarGrafoAliancasPublicas();
        double dist = graphbase.GraphAlgorithms.shortestPathEdges(grafo_aliancas_publicas, p_source, p_target, path);

        if (dist == SEM_CAMINHO) {
            return adicionarAlianca(p_source, p_target, tipoalianca, fator_compatibilidade);

        } else {
            int numPers = path.size();
            //Se o número de Personagens é maior do que 1, então é n-1 ramos
            if (numPers > 1) {
                numPers--;
            }
            double fator_comp = 0;
            Personagem a = null;
            Personagem b = null;
            LinkedList<Personagem> clone = (LinkedList<Personagem>) path.clone();
            while (!clone.isEmpty()) {
                if (a == null && b == null) {
                    a = clone.pop();
                    b = clone.pop();
                    fator_comp = grafo_personagens_aliancas.getEdge(a, b).getWeight();
                } else {
                    a = b;
                    b = clone.pop();
                    fator_comp += grafo_personagens_aliancas.getEdge(a, b).getWeight();
                }
            }
            double mediaComp = fator_comp / numPers;
            grafo_personagens_aliancas.insertEdge(p_source, p_target, tipoalianca, mediaComp);
            return true;
        }
    }

    //===================================2 E====================================
    //Criar um novo grafo representando todas as novas alianças que podem ser realizadas entre todas as personagens
    //caso todas as alianças existentes fossem públicas
    /**
     * Método que dá todas as novas possíveis alianças entre personagens
     *
     * @return Grafo com as possíveis novas alianças
     */
    public Graph<Personagem, Boolean> possiveisNovasAliancas() {

        Graph<Personagem, Boolean> ng = (Graph<Personagem, Boolean>) grafo_personagens_aliancas.clone();
        for (Personagem p_a : ng.vertices()) {
            for (Personagem p_b : ng.vertices()) {
                if (!p_a.equals(p_b) && ng.getEdge(p_a, p_b) != null) {
                    for (Personagem p_c : ng.vertices()) {
                        if (!p_b.equals(p_c) && !p_c.equals(p_a) && ng.getEdge(p_a, p_c) != null) {
                            if (ng.getEdge(p_b, p_c) == null) {
                                double fator = determinarFatorCompatibilidade(p_b, p_c);
                                ng.insertEdge(p_b, p_c, Boolean.TRUE, fator);
                            }
                        }
                    }
                }
            }
        }
        return ng;
    }
    //===================================3F====================================

    /*Verificar	se uma personagem pode conquistar, junto com um dos seus aliados, um determinado local	X 
    (assuma que	o dono	de X, caso exista, não usa as suas alianças), devolvendo qual o aliado,	assim como o
valor necessário e a lista mínima de locais intermédios	a conquistar,caso seja necessário.De notar que o
aliado não pode ser dono de X nem de nenhum dos locais intermédios.*/
    public ConquistaComAliado conquistarComAliados(Personagem a, Local target) {
        final double INVALIDO = -1;
        if (!grafo_personagens_aliancas.validVertex(a) || !grafo_locais_estradas.checkVertex(target)) {
            return null;
        }
        ArrayList<ConquistaComAliado> listaConquista = new ArrayList<>();
        for (Personagem aliado : grafo_personagens_aliancas.adjVertices(a)) {
            Local localassociado = obterLocalAssociadoAPersonagem(aliado);
            if (localassociado != null) {
                if (!obterLocalAssociadoAPersonagem(aliado).equals(target)) {
                    Conquista cq = verificarConquista(aliado, target);
                    if (cq.consegueConquistar()) {
                        ConquistaComAliado cqal = new ConquistaComAliado(cq, aliado);
                        listaConquista.add(cqal);
                    }
                }
            }
        }
        if (listaConquista.isEmpty()) {
            return new ConquistaComAliado(false, INVALIDO, new LinkedList<Local>(), null);
        } else {
            ConquistaComAliado conquistamenorforca = null;
            for (ConquistaComAliado cq : listaConquista) {
                if (conquistamenorforca == null) {
                    conquistamenorforca = cq;
                } else {
                    if (cq.forcaNecessaria() < conquistamenorforca.forcaNecessaria()) {
                        conquistamenorforca = cq;
                    }
                }
            }
            return conquistamenorforca;
        }

    }

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
        if (!grafo_locais_estradas.checkVertex(l)) {
            return grafo_locais_estradas.insertVertex(l);
        }
        return false;
    }

    /**
     * Método para adicionar um local ao grafo_locais_estradas
     *
     * @param local Local
     * @return True or False
     */
    public boolean adicionarLocal(Local local) {
        if (!grafo_locais_estradas.checkVertex(local)) {
            return grafo_locais_estradas.insertVertex(local);
        }
        return false;
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
        if (!grafo_locais_estradas.checkVertex(l_a) || !grafo_locais_estradas.checkVertex(l_b)) {
            return false;
        }
        if (grafo_locais_estradas.getEdge(l_a, l_b) == null) {
            return grafo_locais_estradas.insertEdge(l_a, l_b, custo_estrada);
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
        if (!grafo_personagens_aliancas.validVertex(p)) {
            return grafo_personagens_aliancas.insertVertex(p);
        }
        return false;
    }

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
        if (!grafo_personagens_aliancas.validVertex(p_a) || !grafo_personagens_aliancas.validVertex(p_b)) {
            return false;
        }
        if (grafo_personagens_aliancas.getEdge(p_a, p_b) == null) {
            grafo_personagens_aliancas.insertEdge(p_a, p_b, tipoAlianca, fator_comp);
            return true;
        } else {
            if (grafo_personagens_aliancas.getEdge(p_a, p_b).getElement() != tipoAlianca) {
                grafo_personagens_aliancas.getEdge(p_a, p_b).setElement(tipoAlianca);
                return true;
            }
        }
        return false;
    }

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

    public Iterable<Personagem> devolverTodasPersonagens() {
        return grafo_personagens_aliancas.vertices();
    }

    public Iterable<Local> devolverTodosLocais() {
        return grafo_locais_estradas.vertices();
    }

    public Graph<Personagem, Boolean> gerarGrafoAliancasPublicas() {
        Graph<Personagem, Boolean> grafo_aliancas_publicas = new Graph<>(false);
        for (Personagem pOrig : grafo_personagens_aliancas.vertices()) {
            for (Personagem pAdj : grafo_personagens_aliancas.adjVertices(pOrig)) {
                if (grafo_personagens_aliancas.getEdge(pOrig, pAdj) != null && grafo_personagens_aliancas.getEdge(pOrig, pAdj).getElement() == true) {
                    grafo_aliancas_publicas.insertEdge(pOrig, pAdj, true, grafo_personagens_aliancas.getEdge(pOrig, pAdj).getWeight());
                }
            }
        }

        return grafo_aliancas_publicas;
    }

    public double determinarFatorCompatibilidade(Personagem pers_a, Personagem pers_b) {
        LinkedList<Personagem> path = new LinkedList<>();
        GraphAlgorithms.shortestPathEdges(grafo_personagens_aliancas, pers_a, pers_b, path);
        int numPers = path.size();
        //Se o número de Personagens é maior do que 1, então é n-1 ramos
        if (numPers > 1) {
            numPers--;
        }
        double fator_comp = 0.0;
        Personagem a = null;
        Personagem b = null;
        LinkedList<Personagem> clone = (LinkedList<Personagem>) path.clone();
        while (!clone.isEmpty()) {
            if (a == null && b == null) {
                a = clone.pop();
                b = clone.pop();
                fator_comp = grafo_personagens_aliancas.getEdge(a, b).getWeight();
            } else {
                a = b;
                b = clone.pop();
                fator_comp += grafo_personagens_aliancas.getEdge(a, b).getWeight();
            }
        }
        double rt = fator_comp / numPers; 
        return rt;

    }
}
