package br.com.slautos.oficina.excel;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import br.com.slautos.oficina.pecas.Peca;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Slf4j
public class CriaArquivoExcel {

    public void criarArquivo(final String nomeArquivo, final List<Peca> pecas) {
    	System.out.println("Gerando o arquivo :" + nomeArquivo);

        try (var workbook = new XSSFWorkbook();
             var outputStream = new FileOutputStream(nomeArquivo)) {
            var planilha = workbook.createSheet("Lista de Peças");
            int numeroDaLinha = 0;

            adicionarCabecalho(planilha, numeroDaLinha++);

            for (Peca peca : pecas) {
                var linha = planilha.createRow(numeroDaLinha++);
                adicionarCelula(linha, 0, peca.getId());
                adicionarCelula(linha, 1, peca.getNome());
                adicionarCelula(linha, 2, peca.getRef());
                adicionarCelula(linha, 3, peca.getQuantidade());
                adicionarCelula(linha, 4, peca.getValor());
            }

            workbook.write(outputStream);
        } catch (FileNotFoundException e) {
        	System.out.println("Arquivo não encontrado: {}"+ nomeArquivo);
        } catch (IOException e) {
        	System.out.println("Erro ao processar o arquivo: {} " +nomeArquivo);
        }
        System.out.println("Arquivo gerado com sucesso!");
    }

    private void adicionarCabecalho(XSSFSheet planilha, int numeroLinha) {
        var linha = planilha.createRow(numeroLinha);
        adicionarCelula(linha, 0, "Id");
        adicionarCelula(linha, 1, "Nome");
        adicionarCelula(linha, 2, "Referencia");
        adicionarCelula(linha, 3, "Quantidade");
        adicionarCelula(linha, 4, "Valor");
    }

    private void adicionarCelula(Row linha, int coluna, String valor) {
        Cell cell = linha.createCell(coluna);
        cell.setCellValue(valor);
    }

    private void adicionarCelula(Row linha, int coluna, int valor) {
        Cell cell = linha.createCell(coluna);
        cell.setCellValue(valor);
    }
}
