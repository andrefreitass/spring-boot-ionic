package com.andre.cursomc.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.andre.cursomc.domain.PagamentoComBoleto;

@Service
public class BoletoService {

	public void preencherPagamentoComBoleto(PagamentoComBoleto pgtBoleto, Date instantePedido) {
		Calendar calendario = Calendar.getInstance();
		calendario.setTime(instantePedido);
		calendario.add(Calendar.DAY_OF_MONTH, 7);
		pgtBoleto.setDataVencimento(calendario.getTime());

	}

}
