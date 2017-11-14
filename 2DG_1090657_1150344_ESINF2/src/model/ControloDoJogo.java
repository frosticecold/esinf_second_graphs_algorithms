/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import graph.AdjacencyMatrixGraph;
import graph.EdgeAsDoubleGraphAlgorithms;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author Raúl Correia <1090657@isep.ipp.pt>
 */
public class ControloDoJogo {

    private AdjacencyMatrixGraph<Local, Double> grafo_locais_estradas;
    private AdjacencyMatrixGraph<Personagem, Alianca> grafo_personagens_aliancas;

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
        grafo_personagens_aliancas = new AdjacencyMatrixGraph<>();

    }

    //===================================1 A====================================
    /**
     * Método para ler os locais de um ficheiro de texto para o grafo de locais
     *
     * @param nomeFicheiro Nome do ficheiro
     */
    public void lerLocais(String nomeFicheiro) {
        Ficheiro f = new Ficheiro();
        f.lerLocais(nomeFicheiro, grafo_locais_estradas, grafo_personagens_aliancas);

    }

    //===================================1 B====================================
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
    /**
     * Método que verifica se uma dada Personagem pode conquistar um determinado
     * local Pressupõe que uma personagem tenha um local atribuido na leitura de
     * ficheiros
     *
     * @param pers Personagem
     * @param target Local a conquistar
     * @return Uma conquista que tem como atributo se consegue conquistar, a
     * dificuldade do caminho e o caminho intermédio entre personagem e local
     */
    public Conquista verificarConquista(Personagem pers, Local target) {
        if (!grafo_personagens_aliancas.checkVertex(pers) || !grafo_locais_estradas.checkVertex(target)) {
            LinkedList<Local> naotemcaminho = new LinkedList<>();
            return new Conquista(false, -1, naotemcaminho);
        }
        Local source = obterLocalAssociadoAPersonagem(pers);
        LinkedList<Local> menorCaminho = caminhoComMenorDificuldade(source, target);
        if (!menorCaminho.isEmpty()) {
            LinkedList<Local> caminhointermedio = (LinkedList<Local>) menorCaminho.clone();
            Local local_a = source;
            Local local_b = null;
            double dificuldade = 0;

            //Remover o caminho inicial
            if (menorCaminho.peek() == source) {
                menorCaminho.pop();
            }
            while (!menorCaminho.isEmpty()) {
                if (local_a == source && local_b == null) {
                    local_b = menorCaminho.pop();
                    if (local_b.getDono() != pers) {
                        dificuldade += grafo_locais_estradas.getEdge(local_a, local_b) + local_b.getDificuldade();
                        if (local_b.getDono() != null) {
                            dificuldade += local_b.getDono().getForca();
                        }
                    }
                } else {
                    local_a = local_b;
                    local_b = menorCaminho.pop();
                    if (local_b.getDono() != pers) {
                        dificuldade += local_b.getDificuldade() + grafo_locais_estradas.getEdge(local_a, local_b);
                        if (local_b.getDono() != null) {
                            dificuldade += local_b.getDono().getForca();
                        }
                    }
                }

            }
            if (caminhointermedio.peekFirst() == source) {
                caminhointermedio.removeFirst();
            }
            if (caminhointermedio.peekLast() == target) {
                caminhointermedio.removeLast();
            }
            boolean consegue_conquistar = false;
            if (pers.getForca() > dificuldade) {
                consegue_conquistar = true;
            }
            Conquista cq = new Conquista(consegue_conquistar, dificuldade, caminhointermedio);
            return cq;
        }
        return new Conquista(false, -1, menorCaminho);
    }

    public boolean adicionarLocal(String nomeLocal, int dificuldade, Personagem p) {
        Local l = new Local(nomeLocal, dificuldade, p);
        if (!grafo_locais_estradas.checkVertex(l)) {
            return grafo_locais_estradas.insertVertex(l);
        }
        return false;
    }

    public boolean adicionarLocal(Local local) {
        if (!grafo_locais_estradas.checkVertex(local)) {
            return grafo_locais_estradas.insertVertex(local);
        }
        return false;
    }

    public boolean adicionarEstrada(Local a, Local b, double e) {
        if (grafo_locais_estradas.getEdge(a, b) == null) {
            return grafo_locais_estradas.insertEdge(a, b, e);
        }
        return false;
    }

    public boolean adicionarPersonagem(Personagem p) {
        if (!grafo_personagens_aliancas.checkVertex(p)) {
            return grafo_personagens_aliancas.insertVertex(p);
        }
        return false;
    }

    public boolean adicionarAlianca(Personagem a, Personagem b, boolean tipoAlianca, float fator_comp) {
        if (!grafo_personagens_aliancas.checkVertex(a) || !grafo_personagens_aliancas.checkVertex(b)) {
            return false;
        }
        if (grafo_personagens_aliancas.getEdge(a, b) == null) {
            grafo_personagens_aliancas.insertEdge(a, b, new Alianca(tipoAlianca, fator_comp));
            return true;
        }
        return false;
    }

    public double obterEstrada(Local a, Local b) {
        return grafo_locais_estradas.getEdge(a, b);
    }

    public Personagem obterPersonagemPorNome(String nome) {
        for (Personagem p : grafo_personagens_aliancas.vertices()) {
            if (p.getNome().equalsIgnoreCase(nome)) {
                return p;
            }
        }
        return null;
    }

    public Local obterLocalPorNome(String nome) {
        for (Local l : grafo_locais_estradas.vertices()) {
            if (l.getNome().equalsIgnoreCase(nome)) {
                return l;
            }
        }
        return null;
    }

    public int numEstradas() {
        return grafo_locais_estradas.numEdges();
    }

    public int numLocais() {
        return grafo_locais_estradas.numVertices();
    }

    public int numPersonagens() {
        return grafo_personagens_aliancas.numVertices();
    }

    public int numAliancas() {
        return grafo_personagens_aliancas.numEdges();
    }

    public int numAliadosDiretos(Personagem source) {
        if (!grafo_personagens_aliancas.checkVertex(source)) {
            return 0;
        }
        Iterable<Personagem> itrbl = grafo_personagens_aliancas.directConnections(source);
        int num = 0;
        for (Personagem p : itrbl) {
            num++;
        }
        return num;
    }

    public float obterFatorCompAlianca(Personagem a, Personagem b) {
        if (!grafo_personagens_aliancas.checkVertex(a) || !grafo_personagens_aliancas.checkVertex(b)) {
            return -1f;
        }
        return grafo_personagens_aliancas.getEdge(a, b).getFatorCompatibilidade();
    }

    public AdjacencyMatrixGraph<Personagem, Alianca> cloneAliancas() {
        return (AdjacencyMatrixGraph< Personagem, Alianca>) grafo_personagens_aliancas.clone();
    }

    //2a Construir o grafo  a partir de um ficheiro de texto
    public void lerAlianca(String nomeFicheiro) {
        Ficheiro f = new Ficheiro();
        f.lerPersonagensAliancas(nomeFicheiro, grafo_locais_estradas, grafo_personagens_aliancas);
    }

    //2b Devolver uma lista com todos os aliados de uma dada personagem.
    public Iterable<Personagem> devolverTodosAliados(Personagem source) {
        return grafo_personagens_aliancas.directConnections(source);
    }

    //2c Determinar qual a aliança mais forte, retornando a força e as personagens dessa aliança
    public ForcaAlianca determinarAliancaMaisForte() {
        Personagem p_a = null;
        Personagem p_b = null;

        double forca_alianca = -1;
        for (Personagem p : grafo_personagens_aliancas.vertices()) {
            for (Personagem pAdj : grafo_personagens_aliancas.directConnections(p)) {
                if (forca_alianca == -1) {
                    p_a = p;
                    p_b = pAdj;
                    forca_alianca = (p.getForca() + pAdj.getForca()) * grafo_personagens_aliancas.getEdge(p, pAdj).getFatorCompatibilidade();
                } else {
                    double outra_forca = (p.getForca() + pAdj.getForca()) * grafo_personagens_aliancas.getEdge(p, pAdj).getFatorCompatibilidade();
                    if (outra_forca > forca_alianca) {
                        p_a = p;
                        p_b = pAdj;
                        forca_alianca = outra_forca;
                    }

                }
            }
        }
        if (p_a != null && p_b != null) {

            ForcaAlianca fa = new ForcaAlianca(forca_alianca, p_a, p_b);
            return fa;

        }
        return null;
    }

    public boolean novaAlianca(Personagem p_a, Personagem p_b, boolean tipoalianca, float fator_compatibilidade) {
        if (!grafo_personagens_aliancas.checkVertex(p_a) || !grafo_personagens_aliancas.checkVertex(p_b)) {
            return false;
        }
        if (grafo_personagens_aliancas.getEdge(p_a, p_b) != null) {
            return false;
        }
        AdjacencyMatrixGraph<Personagem, Double> mapaAliancaPeso = copiarAliancaParaAliancaComPeso(grafo_personagens_aliancas);
        LinkedList<Personagem> path = new LinkedList<>();
        double dist = graph.EdgeAsDoubleGraphAlgorithms.shortestPath(mapaAliancaPeso, p_a, p_b, path);
        if (dist == -1) {
            grafo_personagens_aliancas.insertEdge(p_a, p_b, new Alianca(tipoalianca, fator_compatibilidade));
            return true;
        } else {
            int numPersonagens = path.size();
            float fator_comp = 0f;
            Personagem a = null;
            Personagem b = null;
            LinkedList<Personagem> clone = (LinkedList<Personagem>) path.clone();
            while (!clone.isEmpty()) {
                if (a == null && b == null) {
                    a = clone.pop();
                    b = clone.pop();
                    fator_comp = grafo_personagens_aliancas.getEdge(a, b).getFatorCompatibilidade();
                } else {
                    a = b;
                    b = clone.pop();
                    fator_comp += grafo_personagens_aliancas.getEdge(a, b).getFatorCompatibilidade();
                }
            }
            float mediaComp = fator_comp / numPersonagens;
            grafo_personagens_aliancas.insertEdge(p_a, p_b, new Alianca(tipoalianca, mediaComp));
            return true;
        }
    }

    //2e Criar um novo grafo representando todas as novas alianças que podem ser realizadas entre todas as personagens
    //caso todas as alianças existentes fossem públicas
    public AdjacencyMatrixGraph<Personagem, Alianca> possiveisNovasAliancas() {
        AdjacencyMatrixGraph<Personagem, Alianca> newgraph = graph.GraphAlgorithms.transitiveClosure(grafo_personagens_aliancas, new Alianca(true, 1));
        for (Personagem pOrig : grafo_personagens_aliancas.vertices()) {
            for (Personagem pAdj : grafo_personagens_aliancas.directConnections(pOrig)) {
                if (newgraph.getEdge(pOrig, pAdj) != null) {
                    newgraph.removeEdge(pOrig, pAdj);
                }
            }
        }

        return newgraph;
    }

    /*Verificar	se uma personagem pode conquistar, junto com um dos seus aliados, um determinado local	X 
    (assuma que	o dono	de X, caso exista, não usa as suas alianças), devolvendo qual o aliado,	assim como o
valor necessário e a lista mínima de locais intermédios	a conquistar,caso seja necessário.De notar que o
aliado não pode ser dono de X nem de nenhum dos locais intermédios.*/
    public Conquista conquistarComAliado(Personagem pOrig, Local target) {

        return null;
    }

    private AdjacencyMatrixGraph<Personagem, Double> copiarAliancaParaAliancaComPeso(AdjacencyMatrixGraph<Personagem, Alianca> mapa) {
        AdjacencyMatrixGraph<Personagem, Double> outromap = new AdjacencyMatrixGraph<>();
        double PESO = 1;
        for (Personagem p : mapa.vertices()) {
            if (!outromap.checkVertex(p)) {
                outromap.insertVertex(p);
            }
            for (Personagem pAdj : mapa.directConnections(p)) {
                if (!outromap.checkVertex(pAdj)) {
                    outromap.insertVertex(pAdj);
                }
                outromap.insertEdge(p, pAdj, PESO);
            }

        }
        return outromap;
    }

    private Local obterLocalAssociadoAPersonagem(Personagem p) {
        if (!grafo_personagens_aliancas.checkVertex(p)) {
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
                lerLocais(LOCAIS_S);
                lerAlianca(PERSONAGEM_S);
                break;
            case FICH_M:
                lerLocais(LOCAIS_M);
                lerAlianca(PERSONAGEM_M);
                break;
            case FICH_L:
                lerLocais(LOCAIS_L);
                lerAlianca(PERSONAGEM_L);
                break;
            case FICH_XL:
                lerLocais(LOCAIS_XL);
                lerAlianca(PERSONAGEM_XL);
                break;
            case FICH_TESTE:
                lerLocais(LOCAIS_TESTE);
                lerAlianca(PERSONAGEM_TESTE);
                break;
            default:
                break;
        }

    }
}
