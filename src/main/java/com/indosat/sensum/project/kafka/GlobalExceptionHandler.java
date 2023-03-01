package com.indosat.sensum.project.kafka;

import com.indosat.sensum.project.model.Response;
import org.apache.kafka.common.errors.BrokerIdNotRegisteredException;
import org.apache.kafka.common.errors.TimeoutException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletResponse;


@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	@ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
	@ResponseBody
	public Response handleMediaTypeNotSupport(Exception ex) {
		return new Response("415",ex.getMessage());
	}
	
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
	@ResponseBody
	public Response handleMediaTypeNotSupport(HttpServletResponse response, Exception ex) {
		return new Response("405" ,ex.getMessage());
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public Response handleNotReadable(Exception ex) {
		return new Response("400" ,ex.getMessage());
	}
	
	@ExceptionHandler(NullPointerException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	public Response handleNoContent(Exception ex) {
		return new Response("404" ,ex.getMessage());
	}

	@ExceptionHandler(TimeoutException.class)
	@ResponseStatus(HttpStatus.REQUEST_TIMEOUT)
	@ResponseBody
	public Response handleTimeOut(Exception ex) {
		return new Response("408" ,ex.getMessage());
	}
	
	@ExceptionHandler(BrokerIdNotRegisteredException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	public Response handleBrokerIdNotRegister(Exception ex) {
		return new Response("404" ,ex.getMessage());
	}
}
