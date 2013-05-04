package de.wehub.lanetworking;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URL;

public class LAUploadRequest extends LAAbstractRequest {

	protected String _path = null;
	
	public LAUploadRequest(String url, String path) throws Exception {
		super(new URL(url));
		_path = path;
	}

	@Override
	protected void execute() throws Exception {
		
		File upload = new File(_path);

		String crlf = "\r\n";
		String twoHyphens = "--";
		String boundary =  "*****";
		
		_connection.setUseCaches(false);
		_connection.setDoOutput(true);
		_connection.setDoInput(true);

		_connection.setRequestMethod("POST");
		_connection.setRequestProperty("Connection", "Keep-Alive");
		_connection.setRequestProperty("Cache-Control", "no-cache");
		_connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
		
		DataOutputStream request = new DataOutputStream(_connection.getOutputStream());

		request.writeBytes(twoHyphens + boundary + crlf);
		//request.writeBytes("Content-Disposition: form-data; name=\"file\";filename=" + "test.jpg" + crlf);
		//request.writeBytes("Content-Type: image/jpeg");
		
		request.write(String.format("Content-Disposition:form-data;name=\"file\";filename=\"%s\"\r\nContent-Type: image/jpg\r\n\r\n", "test.jpg").getBytes());
		
		FileInputStream in = new FileInputStream(upload);

		int bytesAvailable = in.available();
        int maxBufferSize = 4096;
        // int bufferSize = Math.min(bytesAvailable, maxBufferSize);
        byte[] buffer = new byte[bytesAvailable];

        // read file and write it into form...

        int bytesRead = in.read(buffer, 0, bytesAvailable);

        while (bytesRead > 0) {
        	request.write(buffer, 0, bytesAvailable);
            bytesAvailable = in.available();
            bytesAvailable = Math.min(bytesAvailable, maxBufferSize);
            bytesRead = in.read(buffer, 0, bytesAvailable);
        }

        // send multipart form data necesssary after file data...

        request.writeBytes(crlf);
        request.writeBytes(twoHyphens + boundary + twoHyphens + crlf + crlf);
		
		in.close();

		
		request.flush();
		request.close();
		
		
	}

}
