package com.gust.armazenamento;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import com.gust.placar.TipoPonto;
import com.gust.user.UserNotFoundException;

public class ArmazenamentoXML extends Armazenamento {

	private File arquivoXML;

	public ArmazenamentoXML(File testXMLFile) {
		arquivoXML = testXMLFile;
	}

	@Override
	public void addPontos(String username, TipoPonto tipoPonto, int pontos)
			throws UserNotFoundException, FalhaArmazenamentoException {
		if (isInvalid(pontos) || isInvalid(tipoPonto) || isInvalid(username))
			throw new IllegalArgumentException();
		Document doc = montaDOM();
		NodeList nodesUser = pegaUserNodes(doc);
		Element userElement = procuraUserNoXML(nodesUser, username);
		atualizaPontuacaoNoXML(userElement, tipoPonto, pontos);
		salvaMudancasNoArquivo(doc);
	}

	@Override
	public int getPontos(String username, TipoPonto tipoPonto)
			throws UserNotFoundException, FalhaArmazenamentoException {
		if (isInvalid(tipoPonto) || isInvalid(username))
			throw new IllegalArgumentException();
		Document doc = montaDOM();
		NodeList nodesUser = pegaUserNodes(doc);
		Element userElement = procuraUserNoXML(nodesUser, username);
		return pontuacaoNoXML(userElement, tipoPonto);

	}

	@Override
	public List<TipoPonto> getTiposDePontosJaRegistrados(String username) throws UserNotFoundException, FalhaArmazenamentoException {
		if (isInvalid(username)) throw new IllegalArgumentException();
		var tiposRegistrados = new ArrayList<TipoPonto>();
		
		Document doc = montaDOM();
		NodeList nodesUser = pegaUserNodes(doc);
		Element userElement = procuraUserNoXML(nodesUser, username);
		
		for (TipoPonto tipoPonto : TipoPonto.values()) {
			if (pontuacaoNoXML(userElement, tipoPonto) > 0)
				tiposRegistrados.add(tipoPonto);
		}		
		return tiposRegistrados;		
	}

	@Override
	public List<String> getUsuariosPorTipoPonto(TipoPonto tipoPonto) throws FalhaArmazenamentoException {
		if(isInvalid(tipoPonto)) throw new IllegalArgumentException();
		Document doc = montaDOM();
		NodeList userNodes = pegaUserNodes(doc);
		List<String> listaUsers = buscaUsernamesNoXMLComTipoPonto(userNodes, tipoPonto);
		return listaUsers;
				
	}

	private Document montaDOM() throws FalhaArmazenamentoException {
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(arquivoXML.getAbsolutePath()); // Monta o DOM
			return doc;
		} catch (Exception e) {
			throw new FalhaArmazenamentoException("Ocorreu um problema no acesso ao XML");
		}
	}

	private Element procuraUserNoXML(NodeList nodesUser, String username) throws UserNotFoundException {
		for (int i = 0; i < nodesUser.getLength(); i++) {
			// Captura o Node desse indice
			Node node = nodesUser.item(i);

			boolean nodeEhUmElemento = node.getNodeType() == Node.ELEMENT_NODE;
			// Se node é um Elemento, pode fazer Cast
			if (nodeEhUmElemento) {
				Element userElement = (Element) node;
				// Se o atributo "username" desse Elemento (TAG) é igual ao buscado
				if (username.equals(userElement.getAttribute("username")))
					return userElement; // Retorno o Elemento
			}
		}
		throw new UserNotFoundException("Usuario nao encontrado.");
	}
	
	private List<String> buscaUsernamesNoXMLComTipoPonto(NodeList nodesUser, TipoPonto tipoPonto) {
		var listaUsers = new ArrayList<String>();

		for (int i = 0; i < nodesUser.getLength(); i++) {
			Node node = nodesUser.item(i);
			boolean nodeEhUmElemento = node.getNodeType() == Node.ELEMENT_NODE;
			
			// Se node é um elemento e seu atributo username corresponde
			if (nodeEhUmElemento) {
				if (pontuacaoNoXML((Element) node, tipoPonto) > 0)
					listaUsers.add(((Element) node).getAttribute("username"));
			}
		}
		return listaUsers;
	}
	
	private NodeList pegaUserNodes(Document doc) {
		// Normaliza o root pra não dar problemas
		doc.getDocumentElement().normalize();
		 // Pega o root (Users)
		Element rootUsers = doc.getDocumentElement();
		 // Pega os nós filhos do Root (Users)
		NodeList nodesUser = rootUsers.getElementsByTagName("user");
		return nodesUser;
	}

	private int pontuacaoNoXML(Element userElement, TipoPonto tipoPonto) {
		// Pega os nós (só existe um) do tipo do ponto desejado
		NodeList pontoNodeList = userElement.getElementsByTagName(tipoPonto.toString().toLowerCase());
		// Faz um cast do Node para Element.
		Element pontoElement = ((Element) pontoNodeList.item(0));
		// Retorna o valor em int do atributo "total" (de pontos)
		return Integer.valueOf(pontoElement.getAttribute("total"));
	}

	private void atualizaPontuacaoNoXML(Element userElement, TipoPonto tipoPonto, int pontos) {
		int pontosAtuais = pontuacaoNoXML(userElement, tipoPonto);
		// Pegas os nós (só existe um) do tipo do ponto desejado
		NodeList pontoNodes = userElement.getElementsByTagName(tipoPonto.toString().toLowerCase());
		// Faz um cast do Node para Element.
		Element pontoElement = ((Element) pontoNodes.item(0));
		// Atualiza o valor do atributo "total" (de pontos)
		pontoElement.setAttribute("total", String.valueOf(pontosAtuais + pontos));
	}

	private void salvaMudancasNoArquivo(Document doc) throws FalhaArmazenamentoException {
		try {
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(arquivoXML.getAbsolutePath()));
			transformer.transform(source, result);
		} catch (Exception e) {
			throw new FalhaArmazenamentoException("Falha ao salvar mudanças no arquivo.");
		}
	}

	private boolean isInvalid(TipoPonto tipoPonto) {
		return tipoPonto == null;
	}

	private boolean isInvalid(String username) {
		return (username == null || username.isBlank());
	}

	private boolean isInvalid(int pontos) {
		return pontos <= 0;
	}

}
