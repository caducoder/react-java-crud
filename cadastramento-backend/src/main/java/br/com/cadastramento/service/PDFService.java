package br.com.cadastramento.service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

import br.com.cadastramento.model.Cliente;

@Stateless
public class PDFService {

	@Inject
	private ClienteService clienteService;

	public byte[] gerarRelatorio() {
		List<Cliente> lista = clienteService.listar();
		Document documento = new Document();
		try {
			ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
			PdfWriter writer = PdfWriter.getInstance(documento, byteArrayOut);
			writer.setPageEvent(new FooterTable());
			documento.open();
			documento.addTitle("relatorio");
			Paragraph Titulo = new Paragraph("Relatório de Clientes",
					FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18));
			Titulo.setAlignment(Element.ALIGN_CENTER);
			documento.add(Titulo);
			Paragraph pulaLinha = new Paragraph(" ");
			documento.add(pulaLinha);
			// criar uma tabela
			PdfPTable tabela = new PdfPTable(3);
			tabela.setPaddingTop(10F);
			PdfPCell colNome = new PdfPCell(new Paragraph("Nome", new Font(FontFamily.HELVETICA, 14, Font.BOLD)));
			PdfPCell colIdade = new PdfPCell(new Paragraph("Idade", new Font(FontFamily.HELVETICA, 14, Font.BOLD)));
			PdfPCell colEstado = new PdfPCell(new Paragraph("Estado", new Font(FontFamily.HELVETICA, 14, Font.BOLD)));
			colNome.setBorderColor(BaseColor.BLACK);
			colNome.setBorderWidthBottom(1.5F);
			colNome.setHorizontalAlignment(1);
			colNome.setPaddingBottom(2F);
			colNome.setBackgroundColor(BaseColor.LIGHT_GRAY);
			colEstado.setBorderColor(BaseColor.BLACK);
			colEstado.setBorderWidthBottom(1.5F);
			colEstado.setHorizontalAlignment(1);
			colEstado.setPaddingBottom(2F);
			colEstado.setBackgroundColor(BaseColor.LIGHT_GRAY);
			colIdade.setBorderColor(BaseColor.BLACK);
			colIdade.setBorderWidthBottom(1.5F);
			colIdade.setHorizontalAlignment(1);
			colIdade.setPaddingBottom(2F);
			colIdade.setBackgroundColor(BaseColor.LIGHT_GRAY);

			tabela.addCell(colNome);
			tabela.addCell(colIdade);
			tabela.addCell(colEstado);
			// popular tabela
			for (Cliente cliente : lista) {
				tabela.addCell(cliente.getNome());
				tabela.addCell(String.valueOf(cliente.getIdade()));
				tabela.addCell(cliente.getEstado());
			}
			documento.add(tabela);
			String data = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm").format(LocalDateTime.now());
			Paragraph dataAtual = new Paragraph(data);
			dataAtual.setAlignment(Element.ALIGN_RIGHT);
			documento.add(pulaLinha);
			documento.add(dataAtual);

			documento.close();
			return byteArrayOut.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
			documento.close();
			return null;
		}

	}

	public class FooterTable extends PdfPageEventHelper {
		PdfTemplate t;
		Image total;

		@Override
		public void onOpenDocument(PdfWriter writer, Document document) {
			//cria uma template que vai ser onde o numero total de páginas será inserido
			t = writer.getDirectContent().createTemplate(30, 16);
			try {
				total = Image.getInstance(t);
				total.setRole(PdfName.ARTIFACT);
			} catch (DocumentException de) {
				throw new ExceptionConverter(de);
			}
		}

		@Override
		public void onEndPage(PdfWriter writer, Document document) {// função que roda assim que chega no final da página
			PdfPTable rodape = new PdfPTable(2);
			try {
				total = Image.getInstance(t);
				total.setRole(PdfName.ARTIFACT);

				rodape.setWidths(new int[] { 20, 2 });
				rodape.setTotalWidth(523);
				PdfPCell cell = new PdfPCell(new Phrase(String.format("%d /", writer.getPageNumber())));
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setBorder(Rectangle.NO_BORDER);
				rodape.addCell(cell);
				//adicionando a celula que vai guardar o número, junto com o template
				PdfPCell totalPageCount = new PdfPCell(total);

				totalPageCount.setBorder(Rectangle.NO_BORDER);
				totalPageCount.setHorizontalAlignment(Element.ALIGN_LEFT);
				rodape.addCell(totalPageCount);

				PdfContentByte canvas = writer.getDirectContent();
				canvas.beginMarkedContentSequence(PdfName.ARTIFACT);
				rodape.writeSelectedRows(0, -1, 36, 64, canvas);
				canvas.endMarkedContentSequence();
			} catch (DocumentException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void onCloseDocument(PdfWriter writer, Document document) {
			//posicionando o numero no template, antes de fechar
			ColumnText.showTextAligned(t, Element.ALIGN_LEFT, new Phrase(String.valueOf(writer.getPageNumber())),
					2, 2.4F, 0);
		}
	}

}
