package com.andre.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.andre.cursomc.domain.enums.TipoCliente;
import com.andre.cursomc.dto.ClienteNewDTO;
import com.andre.cursomc.resources.exception.FieldMessage;
import com.andre.cursomc.services.validation.utils.CpfCnpj;

public class ClienteInsertValidator implements ConstraintValidator<ClienteValidator, ClienteNewDTO> {

	@Override
	public void initialize(ClienteValidator ann) {
	}

	@Override
	public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();
		
		if(objDto.getTipo() == null) {
			list.add(new FieldMessage("tipo","Tipo nao pode ser nulo"));
		}
		if (objDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCodigo()) && !CpfCnpj.isValidCPF(objDto.getCpfOuCnpj())) {
			list.add(new FieldMessage("cpfOuCnpj","CPF Invalido"));
		}
		if (objDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCodigo()) && !CpfCnpj.isValidCNPJ(objDto.getCpfOuCnpj())) {
			list.add(new FieldMessage("cpfOuCnpj","CNPJ Invalido"));
		}
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}