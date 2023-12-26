package in.ashokit.service;

import java.io.IOException;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.CMYKColor;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import in.ashokit.binding.SearchCriteria;
import in.ashokit.entity.CitizenPlan;
import in.ashokit.repo.CitizenPlanRepo;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class CitizenPlanServiceImpl implements CitizenPlanService {

	@Autowired
	private CitizenPlanRepo repo;

	@Override
	public List<String> getPlanNames() {
		return repo.getPlanNames();
	}

	@Override
	public List<String> getPlanStatus() {
		return repo.getPlanStatus();
	}

	@Override
	public List<CitizenPlan> searchCitizens(SearchCriteria criteria) {
		CitizenPlan entity = new CitizenPlan();
		if (StringUtils.isNotBlank(criteria.getPlanName())) {
			entity.setPlanName(criteria.getPlanName());
		}
		if (StringUtils.isNotBlank(criteria.getPlanStatus())) {
			entity.setPlanStatus(criteria.getPlanStatus());
		}
		if (StringUtils.isNotBlank(criteria.getGender())) {
			entity.setGender(criteria.getGender());
		}
		if (criteria.getPlanStartDate() != null) {
			entity.setPlanStartDate(criteria.getPlanStartDate());
		}
		if (criteria.getPlanEndDate() != null) {
			entity.setPlanEndDate(criteria.getPlanEndDate());
		}

		Example<CitizenPlan> of = Example.of(entity);

		return repo.findAll(of);
	}

	@Override
	public void generateExcel(HttpServletResponse response) throws Exception {

		List<CitizenPlan> records = repo.findAll();

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("Data");

		// set the data Header rows
		HSSFRow headerRow = sheet.createRow(0);
		headerRow.createCell(0).setCellValue("Name");
		headerRow.createCell(1).setCellValue("Email");
		headerRow.createCell(2).setCellValue("Gender");
		headerRow.createCell(3).setCellValue("SSN");
		headerRow.createCell(4).setCellValue("Plan Name");
		headerRow.createCell(5).setCellValue("Plan Status");

		int rowIndex = 1;

		for (CitizenPlan record : records) {
			HSSFRow dataRow = sheet.createRow(rowIndex);
			dataRow.createCell(0).setCellValue(record.getName());
			dataRow.createCell(1).setCellValue(record.getEmail());
			dataRow.createCell(2).setCellValue(record.getGender());
			dataRow.createCell(3).setCellValue(record.getSsn());
			dataRow.createCell(4).setCellValue(record.getPlanName());
			dataRow.createCell(5).setCellValue(record.getPlanStatus());

			rowIndex++;
		}
		ServletOutputStream outputStream = response.getOutputStream();
		workbook.write(outputStream);
		workbook.close();
		outputStream.close();
	}

	@Override
	public void generatePdf(HttpServletResponse response) throws IOException {

		Document pdfDoc = new Document(PageSize.A4);
		ServletOutputStream outputStream = response.getOutputStream();
		PdfWriter.getInstance(pdfDoc, outputStream);

		pdfDoc.open();

		// this code is for paragraph creation
		Font fontTitle = FontFactory.getFont(FontFactory.TIMES_ROMAN);
		fontTitle.setSize(20);
		Paragraph p = new Paragraph("Citizen Plan Info", fontTitle);
		p.setAlignment(Paragraph.ALIGN_CENTER);
		pdfDoc.add(p);

		PdfPTable table = new PdfPTable(6);
		table.setWidthPercentage(100);
		table.setWidths(new int[] { 3, 3, 3, 3, 3, 3 });
		table.setSpacingBefore(5);

		// this code is for creation pdf table header cell with background color
		PdfPCell cell = new PdfPCell();
		// Setting the background color and padding of the table cell
		cell.setBackgroundColor(CMYKColor.BLUE);
		cell.setPadding(5);
		Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN);
		font.setColor(CMYKColor.WHITE);
		cell.setPhrase(new Phrase("Name", font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Email", font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Gender", font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("SSN", font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Plan Name", font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Plan Status", font));
		table.addCell(cell);

		// adding the records from the database inot pdf
		List<CitizenPlan> records = repo.findAll();
		for (CitizenPlan record : records) {
			table.addCell(record.getName());
			table.addCell(record.getEmail());
			table.addCell(record.getGender());
			table.addCell(String.valueOf(record.getSsn()));
			table.addCell(record.getPlanName());
			table.addCell(record.getPlanStatus());
		}

		pdfDoc.add(table);

		pdfDoc.close();
		outputStream.close();

	}

}
