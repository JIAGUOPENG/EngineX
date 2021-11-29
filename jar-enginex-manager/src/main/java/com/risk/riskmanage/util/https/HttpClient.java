package com.risk.riskmanage.util.https;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.PartBase;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.protocol.Protocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class HttpClient implements java.io.Serializable {
	
	static Logger log=LoggerFactory.getLogger(HttpClient.class);

	private static final long serialVersionUID = -176092625883595547L;
	private static final int OK 				   = 200;						// OK: Success!
	private static final int NOT_MODIFIED 		   = 304;			// Not Modified: There was no new data to return.
	private static final int BAD_REQUEST 		   = 400;				// Bad Request: The request was invalid.  An accompanying error message will explain why. This is the status versionCode will be returned during rate limiting.
	private static final int NOT_AUTHORIZED 	   = 401;			// Not Authorized: Authentication credentials were missing or incorrect.
	private static final int FORBIDDEN 			   = 403;				// Forbidden: The request is understood, but it has been refused.  An accompanying error message will explain why.
	private static final int NOT_FOUND             = 404;				// Not Found: The URI requested is invalid or the resource requested, such as a user, does not exists.
	private static final int NOT_ACCEPTABLE        = 406;		// Not Acceptable: Returned by the Search API when an invalid format is specified in the request.
	private static final int INTERNAL_SERVER_ERROR = 500;// Internal Server Error: Something is broken.  Please post to the group so the Weibo team can investigate.
	private static final int BAD_GATEWAY           = 502;// Bad Gateway: Weibo is down or being upgraded.
	private static final int SERVICE_UNAVAILABLE   = 503;// Service Unavailable: The Weibo servers are up, but overloaded with requests. Try again later. The search and trend methods use this to indicate when you are being rate limited.

	private final static boolean DEBUG = true;
	org.apache.commons.httpclient.HttpClient client = null;

	private MultiThreadedHttpConnectionManager connectionManager;
	private int maxSize;

	public HttpClient() {
		this(150, 30000, 30000, 1024 * 1024);
	}

	public HttpClient(int maxConPerHost, int conTimeOutMs, int soTimeOutMs,
			int maxSize) {
		connectionManager = new MultiThreadedHttpConnectionManager();
		HttpConnectionManagerParams params = connectionManager.getParams();
		params.setDefaultMaxConnectionsPerHost(maxConPerHost);
		params.setConnectionTimeout(conTimeOutMs);
		params.setSoTimeout(soTimeOutMs);

		HttpClientParams clientParams = new HttpClientParams();
		// 忽略cookie 避免 Cookie rejected 警告
		clientParams.setCookiePolicy(CookiePolicy.IGNORE_COOKIES);
		client = new org.apache.commons.httpclient.HttpClient(clientParams,
				connectionManager);
		Protocol myhttps = new Protocol("https", new MySSLSocketFactory(), 443);
		Protocol.registerProtocol("https", myhttps);
		this.maxSize = maxSize;
	}

	/**
	 * log调试
	 * 
	 */
	private static void log(String message) {
		if (DEBUG) {
			log.debug(message);
		}
	}

	/**
	 * 处理http getmethod 请求
	 * 
	 */
	public int getHttps(String url){
		log.debug(" in getHttps,url="+url);
		GetMethod method = new GetMethod(url);
		int responseCode = -1;
		try {
			method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
					new DefaultHttpMethodRetryHandler(3, false));
			client.executeMethod(method);
			responseCode = method.getStatusCode();
			log.info("https StatusCode:" + responseCode);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			method.releaseConnection();
			return responseCode;
		}
	}
	public String get(String url) throws Exception {
		return get(url, new PostParameter[0]).asString();
	}

	public Response get(String url, PostParameter[] params)
			throws Exception {
		log("Request:");
		log("GET:" + url);
		if (null != params && params.length > 0) {
			String encodedParams = HttpClient.encodeParameters(params);
			if (-1 == url.indexOf("?")) {
				url += "?" + encodedParams;
			} else {
				url += "&" + encodedParams;
			}
		}
		GetMethod getmethod = new GetMethod(url);
		return httpRequest(getmethod);

	}

	/**
	 * 处理http post请求
	 * 
	 */

	public Response post(String url, PostParameter[] params)
			throws Exception {
		return post(url, params, true);

	}

	public Response post(String url, PostParameter[] params,
			Boolean WithTokenHeader) throws HttpsException {
		log("Request:");
		log("POST" + url);
		PostMethod postMethod = new PostMethod(url);
		for (int i = 0; i < params.length; i++) {
			postMethod.addParameter(params[i].getName(), params[i].getValue());
		}
		HttpMethodParams param = postMethod.getParams();
		param.setContentCharset("UTF-8");
		if (WithTokenHeader) {
			return httpRequest(postMethod);
		} else {
			return httpRequest(postMethod, WithTokenHeader);
		}
	}

	/**
	 * 处理http post请求
	 * 
	 */

	public String post(String url,  Map<String, String> params)
			throws Exception {
		 String strResult = null;
	        
		 Response response=   post(url,  params, true);
		  if (response !=null) {  
	            // 取出回应字串  
	            strResult = response.getResponseAsString();  
	        }
		  return strResult;
	}

	public Response post(String url, Map<String, String> params,
			Boolean WithTokenHeader) throws HttpsException {
		log("Request:");
		log("POST" + url);
		PostMethod postMethod = new PostMethod(url);
		postMethod.addRequestHeader("Content-type","application/x-www-form-urlencoded; charset=UTF-8");

        for (Map.Entry<String, String> entry : params.entrySet()) {  
        	postMethod.addParameter(entry.getKey(), entry  
                    .getValue());
        }  
			
		HttpMethodParams param = postMethod.getParams();
		param.setContentCharset("UTF-8");
		if (WithTokenHeader) {
			return httpRequest(postMethod);
		} else {
			return httpRequest(postMethod, WithTokenHeader);
		}
	}
	public Response httpRequest(HttpMethod method) throws HttpsException {
		return httpRequest(method, true);
	}

	public Response httpRequest(HttpMethod method, Boolean WithTokenHeader)
			throws HttpsException {
		InetAddress ipaddr;
		int responseCode = -1;
		try {
			ipaddr = InetAddress.getLocalHost();
			List<Header> headers = new ArrayList<Header>();
			method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
					new DefaultHttpMethodRetryHandler(3, false));
			client.executeMethod(method);
			Header[] resHeader = method.getResponseHeaders();
			responseCode = method.getStatusCode();
			log("Response:");
			log("https StatusCode:" + String.valueOf(responseCode));

			for (Header header : resHeader) {
				log(header.getName() + ":" + header.getValue());
			}
			Response response = new Response();
			response.setResponseAsString(method.getResponseBodyAsString());
			log(response.toString() + "\n");

			if (responseCode != OK)

			{
				try {
					throw new HttpsException(getCause(responseCode));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return response;

		} catch (IOException ioe) {
			throw new HttpsException(ioe.getMessage(), ioe, responseCode);
		} finally {
			method.releaseConnection();
		}

	}

	/*
	 * 对parameters进行encode处理
	 */
	public static String encodeParameters(PostParameter[] postParams) {
		StringBuffer buf = new StringBuffer();
		for (int j = 0; j < postParams.length; j++) {
			if (j != 0) {
				buf.append("&");
			}
			try {
				buf.append(URLEncoder.encode(postParams[j].getName(), "UTF-8"))
						.append("=")
						.append(URLEncoder.encode(postParams[j].getValue(),
								"UTF-8"));
			} catch (java.io.UnsupportedEncodingException neverHappen) {
			}
		}
		return buf.toString();
	}

	private static class ByteArrayPart extends PartBase {
		private byte[] mData;
		private String mName;

		public ByteArrayPart(byte[] data, String name, String type)
				throws IOException {
			super(name, type, "UTF-8", "binary");
			mName = name;
			mData = data;
		}

		protected void sendData(OutputStream out) throws IOException {
			out.write(mData);
		}

		protected long lengthOfData() throws IOException {
			return mData.length;
		}

		protected void sendDispositionHeader(OutputStream out)
				throws IOException {
			super.sendDispositionHeader(out);
			StringBuilder buf = new StringBuilder();
			buf.append("; filename=\"").append(mName).append("\"");
			out.write(buf.toString().getBytes());
		}
	}

	private static String getCause(int statusCode) {
		String cause = null;
		switch (statusCode) {
		case NOT_MODIFIED:
			break;
		case BAD_REQUEST:
			cause = "The request was invalid.  An accompanying error message will explain why. This is the status versionCode will be returned during rate limiting.";
			break;
		case NOT_AUTHORIZED:
			cause = "Authentication credentials were missing or incorrect.";
			break;
		case FORBIDDEN:
			cause = "The request is understood, but it has been refused.  An accompanying error message will explain why.";
			break;
		case NOT_FOUND:
			cause = "The URI requested is invalid or the resource requested, such as a user, does not exists.";
			break;
		case NOT_ACCEPTABLE:
			cause = "Returned by the Search API when an invalid format is specified in the request.";
			break;
		case INTERNAL_SERVER_ERROR:
			cause = "Something is broken.  Please post to the group so the Weibo team can investigate.";
			break;
		case BAD_GATEWAY:
			cause = "Weibo is down or being upgraded.";
			break;
		case SERVICE_UNAVAILABLE:
			cause = "Service Unavailable: The Weibo servers are up, but overloaded with requests. Try again later. The search and trend methods use this to indicate when you are being rate limited.";
			break;
		default:
			cause = "";
		}
		return statusCode + ":" + cause;
	}

}
