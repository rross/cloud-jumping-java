package com.xede.dreamforce12.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import com.sun.jersey.api.core.ResourceConfig;

@Path("/calcengine")
public class RemoteExecutionEngineService {
	
	private static final Logger log = Logger.getLogger(RemoteExecutionEngineService.class);
	private static final String JSON_SCRIPT_NAME = "/json2.js";
	private static final String JSON_SCRIPT_ENCODING = "UTF-8";
	private static String jsonScriptContents = null;
	
    @SuppressWarnings("unused")
	@Context
    private UriInfo context;
    @SuppressWarnings("unused")
	@Context
    private ResourceConfig rc;	
	
	public RemoteExecutionEngineService()
	{
		log.debug("The constructor has been called!");
	}
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String ping() 
	{
		log.debug("Ping has been called");
		return "The service is running";
	}
	
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String htmlPing()
	{
		log.debug("htmlPing has been called");
		return "<html><body>The service is <b>running</b></body></html>";
	}
		
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public JaxbList<Engine> availableEngines()
	{
		List<Engine> engines = new ArrayList<Engine>();
		
		try
		{		
			ScriptEngineManager manager = new ScriptEngineManager();
	        List<ScriptEngineFactory> engineFactories = manager.getEngineFactories();
	        for(ScriptEngineFactory sef : engineFactories)
	        {
	        	Engine engine = new Engine(sef.getEngineName(), sef.getEngineVersion(), sef.getNames());
	        	engines.add(engine);
	        }	  
		}
		catch(Exception ex)
		{
			log.error("An error occurred while processing the list of engines", ex);
		}
		
        return new JaxbList<Engine>(engines);
	}
		
	@POST
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public ExecutionResult executeScript(@FormParam("script") String script)
	{
		ExecutionResult executionResult = new ExecutionResult();
		try
		{
			ScriptEngineManager manager = new ScriptEngineManager();
			ScriptEngine engine = manager.getEngineByName("js");
			
			log.debug("exeucting the inclusion of the json class...");
			
			// inject the JSON class
			engine.eval(getJsonScriptContents());
			
			log.debug("getting ready to execute the script: "+script);
			
			// run the requested script
			Object result = engine.eval(script);
			String answer;
			if (result == null)
				answer = null;
			else
				answer = result.toString();
			
			log.debug("Testing script: "+script+" produced the following results: "+answer);
			
			executionResult.setResult(true);
			executionResult.setErrorMessage("");
			executionResult.setAnswer(answer);			
		}
		catch(ScriptException se)
		{
			log.error("An exception occurred while testing script: " + script, se);
			
			executionResult.setResult(false);
			executionResult.setErrorMessage(se.getMessage());
			executionResult.setAnswer(null);
		}
		
		return executionResult;
	}
	
	private static String getJsonScriptContents()
	{
		if (jsonScriptContents == null)
		{			
			try 
			{
				InputStream is = RemoteExecutionEngineService.class.getResourceAsStream(JSON_SCRIPT_NAME);
				StringWriter writer = new StringWriter();
				IOUtils.copy(is, writer, JSON_SCRIPT_ENCODING);
				
				jsonScriptContents = writer.toString();
			} 
			catch (IOException e) 
			{
				log.error("Unable to retrieve the JSON Script Contents", e);
				return null;
			}
			
		}
		
		return jsonScriptContents;
	}
	

}
