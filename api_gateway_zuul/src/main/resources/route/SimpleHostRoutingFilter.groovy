package route

import java.util.concurrent.atomic.AtomicReference;

import javax.servlet.http.HttpServletRequest

import org.apache.http.Header
import org.apache.http.client.HttpClient
import org.apache.http.message.BasicHeader
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import com.netflix.zuul.ZuulFilter
import com.netflix.zuul.context.RequestContext

class SimpleHostRoutingFilter extends ZuulFilter {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SimpleHostRoutingFilter.class)
	
	private static final AtomicReference<HttpClient> CLIENT = new AtomicReference<HttpClient>(newClient());	

	public SimpleHostRoutingFilter() {}
	

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public int filterOrder() {
		return 100;
	}

	@Override
	public String filterType() {
		return 'route';
	}

	@Override
	public Object run() {
		LOGGER.info("{} start",this.getClass().name)
		
		HttpServletRequest request = RequestContext.currentContext.getRequest();
		
		Header[] header = buildZuulRequestHeaders(request)
		String vertb = getVerb(request);
		InputStream requestEntity = getRequestBody(request)
		
		String uri = request.getRequestURI();
		if (RequestContext.currentContext.requestURI !=null) {
			LOGGER.info("RequestContext URI : {}", RequestContext.currentContext.requestURI)
		}
		LOGGER.info("Request URI : {}", uri)
				
		LOGGER.info("{} end",this.getClass().name)
		return null;
	}
	
	def getRequestBody(HttpServletRequest request) {
		Object requestEntity = null
		try {		
			requestEntity = request.getInputStream()
		} catch (IOException e) {
			// no requestBody is ok.
		}
		return requestEntity
	}
	
	def Header[] buildZuulRequestHeaders(HttpServletRequest request) {
		
		if (request == null)
			return null; 
		
		def headers = new ArrayList();
		Enumeration headerNames = request.getHeaderNames();
		for (String name : headerNames) {
			if (isValidHeader(name)) {
				headers.add(new BasicHeader(name,request.getHeader(name)));
			}
		}
		
		LOGGER.debug("HttpServletRequest header : {}", headers);
		
		Map zuulRequestHeaders = RequestContext.getCurrentContext().getZuulRequestHeaders();
		
		zuulRequestHeaders.keySet().each {
			String name = it.toLowerCase();
			BasicHeader h = headers.find { BasicHeader he -> he.name == name }
			if (h != null) {
				headers.remove(h)
			}
			headers.add(new BasicHeader((String) it, (String) zuulRequestHeaders[it]))
		}
		
		if (RequestContext.currentContext.responseGZipped) {
			headers.add(new BasicHeader("accept-encoding", "deflate, gzip"))
		}
		
		LOGGER.debug("ZuulRequest hreader : {}", headers)
		
	}
	
	private String getVerb(HttpServletRequest request) {
		String sMethod = request.getMethod();
		return sMethod.toUpperCase();
	}
	
	private boolean isValidHeader(String name) {
		if (name.toLowerCase().contains("content-length")) return false;
		if (!RequestContext.currentContext.responseGZipped) {
			if (name.toLowerCase().contains("accept-encoding")) return false;
		}
		return true;
	}
	
	private boolean isValidHeader(Header header) {
		switch (header.name.toLowerCase()) {
			case "connection":
			case "content-length":
			case "content-encoding":
			case "server":
			case "transfer-encoding":
				return false
			default:
				return true
		}
	}
	
	private static final HttpClient newClient() {
		
	}
}
