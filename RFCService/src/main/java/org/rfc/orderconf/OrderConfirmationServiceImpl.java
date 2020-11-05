package org.rfc.orderconf;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.rfc.confbuilder.ConfirmationBuilder;
import org.rfc.confbuilder.ConfirmationBuilderFactory;
import org.rfc.exception.RFCException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service("confirmationService")
public class OrderConfirmationServiceImpl implements OrderConfirmationService {
	
	
	private final ConfirmationBuilderFactory builderFactory=new ConfirmationBuilderFactory(); 
	
	@Override
	public void test() throws RFCException {
		

	}

	@Override
	public SupplierConfirmationDTO readConfirmationPdf(MultipartFile mf,String supplier) throws RFCException {
		PDFTextStripper stripper=null;
		PDDocument doc=null;
		String text=null;
		try {
			doc=PDDocument.load(mf.getBytes());
			stripper=new PDFTextStripper();
			//stripper.setStartPage(1);
			//stripper.setEndPage(5);
			text=stripper.getText(doc);
		} 
		catch (IOException e) {
			e.printStackTrace();
			throw new RFCException(200,"Error reading file "+mf.getName());
		}
		ConfirmationBuilder builder=builderFactory.getBuilder(supplier);
		SupplierConfirmationDTO confirmation=builder.build(text);
		//System.out.println(text);
		
		return confirmation;
	}
	
	

}
