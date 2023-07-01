package com.digi.app.component;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

@Slf4j
@Component
public class JasperReportExportComponent {
	private DataSource dataSource;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void exportJrxmlReport(String filePath, Map<String, Object> param, HttpServletResponse response) {
		Connection conn=null;
		try {
			conn=dataSource.getConnection();
			ServletOutputStream servletOutputStream = response.getOutputStream();
			JasperReport jr = JasperCompileManager.compileReport(filePath);
			JasperPrint jp = JasperFillManager.fillReport(jr, param, conn);
			byte[] bytes = JasperExportManager.exportReportToPdf(jp);

			response.getOutputStream().write(bytes);
			
			response.setHeader("Expires", "0");
		      response.setHeader("Cache-Control","must-revalidate, post-check=0, pre-check=0");
		      response.setHeader("Pragma", "public");
			
			response.setContentType("application/pdf");

			
			servletOutputStream.flush();
			servletOutputStream.close();
		} catch (Exception e) {
			log.error("Transaction jasper report exception.\n" + e);

		} finally {
			log.info("Transaction jasper report final called.");
			try {
				conn.close();
			} catch (SQLException e) {
				log.info("Jasper report export Connection close exception\n"+e);
			}
		}
	}
	public void exportJasperReport() {
		
	}
}
