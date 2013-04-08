package de.wehub.lanetworking;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
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

		_connection.setRequestMethod("POST");
		_connection.setRequestProperty("Connection", "Keep-Alive");
		_connection.setRequestProperty("Cache-Control", "no-cache");
		_connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
		
		DataOutputStream request = new DataOutputStream(_connection.getOutputStream());

		request.writeBytes(twoHyphens + boundary + crlf);
		request.writeBytes("Content-Disposition: form-data; name=\"file\"; filename=comment.jpg" + crlf);
		request.writeBytes("Content-Type: image/jpeg");
		request.writeBytes(crlf);
		
		byte[] data = new byte[4096];
		int read = 0;

		FileInputStream in = new FileInputStream(upload);
		while((read = in.read(data, 0, 4096)) != -1) {
			request.write(data, 0, read);
		}
		
		in.close();
		
		request.writeBytes(crlf);
		request.writeBytes(twoHyphens + boundary + twoHyphens + crlf);
		
		request.flush();
		request.close();
		
		
	}

}
