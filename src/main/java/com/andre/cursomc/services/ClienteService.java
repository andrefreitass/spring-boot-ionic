package com.andre.cursomc.services;

import java.awt.image.BufferedImage;
import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.andre.cursomc.domain.Cidade;
import com.andre.cursomc.domain.Cliente;
import com.andre.cursomc.domain.Endereco;
import com.andre.cursomc.domain.enums.PerfilAcesso;
import com.andre.cursomc.domain.enums.TipoCliente;
import com.andre.cursomc.dto.ClienteDTO;
import com.andre.cursomc.dto.ClienteNewDTO;
import com.andre.cursomc.repositories.CidadeRepository;
import com.andre.cursomc.repositories.ClienteRepository;
import com.andre.cursomc.repositories.EnderecoRepository;
import com.andre.cursomc.security.Usuario;
import com.andre.cursomc.services.exception.AuthorizationException;
import com.andre.cursomc.services.exception.DataIntegrityException;
import com.andre.cursomc.services.exception.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repo;

	@Autowired
	private CidadeRepository cidadeRespository;

	@Autowired
	private EnderecoRepository enderecoRepository;

	@Autowired
	private BCryptPasswordEncoder senhaCriptografada;

	@Autowired
	private S3Service s3service;

	@Autowired
	private ImageService imageService;

	@Value("${img.prefix.client.profile}")
	private String prefixo;

	public Cliente find(Integer id) {
		// Verificar se o ID que esta consultando e o mesmo do logado
		Usuario usuario = UsuarioService.usuarioAutenticado();
		if (usuario == null || !usuario.buscaPerfilAcesso(PerfilAcesso.ADMIN) && !id.equals(usuario.getId())) {
			throw new AuthorizationException("Acesso negado o cliente informado nao corresponde ao logado");
		}

		// ...........................................................
		Cliente obj = repo.findOne(id);
		if (obj == null) {
			throw new ObjectNotFoundException("Cliente nao encontrado ID: " + id + ", Tipo " + Cliente.class.getName());
		} else {
			return obj;
		}

	}

	// Metodo para Insercao
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		obj = repo.save(obj);
		enderecoRepository.save(obj.getEnderecos());
		return obj;
	}

	// Metodo para atualizacao
	public Cliente update(Cliente obj) {
		Cliente novoObjeto = find(obj.getId());
		updateObjeto(novoObjeto, obj);
		return repo.save(novoObjeto);
	}

	// Metodo para Exclusao
	public void delete(Integer id) {
		find(id);
		try {
			repo.delete(id);

		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Nao e possivel excluir um Cliente que possui Pedidos");

		}

	}

	// Metodo para buscar Todos Dados
	public List<Cliente> findAll() {
		List<Cliente> lista = repo.findAll();
		if (lista == null) {
			throw new ObjectNotFoundException(
					"Objeto nao encontrado ID: " + findAll() + ", Tipo " + Cliente.class.getName());
		} else {
			return repo.findAll();
		}

	}

	// Metodo para paginacao
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = new PageRequest(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);

	}

	// Converte DTO para Entity
	public Cliente fromDTO(ClienteDTO objDTO) {
		return new Cliente(objDTO.getId(), objDTO.getNome(), objDTO.getEmail(), null, null, null);
	}

	public Cliente fromDTO(ClienteNewDTO objDTO) {
		Cliente cli = new Cliente(null, objDTO.getNome(), objDTO.getEmail(), objDTO.getCpfOuCnpj(),
				TipoCliente.toEnum(objDTO.getTipo()), senhaCriptografada.encode(objDTO.getSenha()));

		Cidade cid = cidadeRespository.findOne(objDTO.getCidadeId());

		Endereco end = new Endereco(null, objDTO.getLogradouro(), objDTO.getNumero(), objDTO.getComplemento(),
				objDTO.getBairro(), objDTO.getCep(), cli, cid);

		cli.getEnderecos().add(end);

		cli.getTelefones().add(objDTO.getTelefone1());
		if (objDTO.getTelefone2() != null) {
			cli.getTelefones().add(objDTO.getTelefone2());
		}
		if (objDTO.getTelefone3() != null) {
			cli.getTelefones().add(objDTO.getTelefone2());
		}
		return cli;
	}

	private void updateObjeto(Cliente novoObjeto, Cliente obj) {
		novoObjeto.setNome(obj.getNome());
		novoObjeto.setEmail(obj.getEmail());
	}

	// Metodo para Enviar a foto de perfil do usuario para AMAZON
	// O Metodo pega o usuario logado, associa a imagem e salva no banco
	public URI uploadProfilePicture(MultipartFile multiPartFile) {
		Usuario usuario = UsuarioService.usuarioAutenticado();
		if (usuario == null) {
			throw new AuthorizationException("Usuario nao encontrado, acesso negado");
		}

		BufferedImage jgpImage = imageService.getJpgImageFromFile(multiPartFile);
		String nomeArquivo = prefixo + usuario.getId() + ".jpg";
		return s3service.uploadFile(imageService.getInputStream(jgpImage, "jpg"), nomeArquivo, "image");

	}

}
