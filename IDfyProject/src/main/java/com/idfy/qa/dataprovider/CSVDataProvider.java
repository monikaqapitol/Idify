package com.idfy.qa.dataprovider;

import java.io.IOException;

public interface CSVDataProvider {

	public Object[] readAllRows() throws IOException;

	public Object[] parseAllRows() throws IOException;

}
