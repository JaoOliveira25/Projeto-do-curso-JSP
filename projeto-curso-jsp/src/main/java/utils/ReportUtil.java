package utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import jakarta.servlet.ServletContext;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;
import net.sf.jasperreports.poi.export.JRXlsExporter;

@SuppressWarnings({ "rawtypes", "unchecked"} )

public class ReportUtil implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
public byte[] geraRelatorioExcel(List listaDados, String nomeRelatorio, HashMap<String, Object> params, ServletContext servletContext) throws Exception {
		
		JRBeanCollectionDataSource jrbcds = new JRBeanCollectionDataSource(listaDados);
		
		String caminhoJasper = servletContext.getRealPath("relatorios")+File.separator+nomeRelatorio+".jasper";
		
		JasperPrint impressoraJasper = JasperFillManager.fillReport(caminhoJasper, params, jrbcds);	
		
		JRXlsExporter exporter = new JRXlsExporter();
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		SimpleXlsReportConfiguration configuration = new SimpleXlsReportConfiguration();
		configuration.setOnePagePerSheet(false);
		configuration.setDetectCellType(true);
		configuration.setWhitePageBackground(false);
		
		exporter.setExporterInput(new SimpleExporterInput(impressoraJasper));
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(baos));
		exporter.setConfiguration(configuration);

		return baos.toByteArray();
	}
	
	public byte[] geraRelatorioPDF(List listaDados, String nomeRelatorio, HashMap<String, Object> params, ServletContext servletContext) throws Exception {
		
		JRBeanCollectionDataSource jrbcds = new JRBeanCollectionDataSource(listaDados);
		
		String caminhoJasper = servletContext.getRealPath("relatorios")+File.separator+nomeRelatorio+".jasper";
		
		JasperPrint impressoraJasper = JasperFillManager.fillReport(caminhoJasper, params, jrbcds);	
	
		return JasperExportManager.exportReportToPdf(impressoraJasper);
	}
	
	
	public byte[] geraRelatorioPDF(List listaDados, String nomeRelatorio, ServletContext servletContext) throws Exception {
		JRBeanCollectionDataSource jrbcds = new JRBeanCollectionDataSource(listaDados);
		
		String caminhoJasper = servletContext.getRealPath("relatorios")+File.separator+nomeRelatorio+".jasper";
		
		JasperPrint impressoraJasper = JasperFillManager.fillReport(caminhoJasper, new HashMap(), jrbcds);	
	
		return JasperExportManager.exportReportToPdf(impressoraJasper);
	}
	
	
	
}
