/**
 * 
 */
package com.devdojo.springboot.error;

/**
 * @author Anderson Indiano on 3 de fev. de 2021.
 *
 */
public class ErrorArgumentResolvers {
	private String title;
	private int status;
	private String detail;
	private long timestamp;
	private String developerMessage;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	public String getDeveloperMessage() {
		return developerMessage;
	}
	public void setDeveloperMessage(String developerMessage) {
		this.developerMessage = developerMessage;
	}
	
	public static final class Builder {
		private String title;
		private int status;
		private String detail;
		private long timestamp;
		private String developerMessage;

		private Builder() {
		}

		public Builder title(String title) {
			this.title = title;
			return this;
		}

		public Builder status(int status) {
			this.status = status;
			return this;
		}

		public Builder detail(String detail) {
			this.detail = detail;
			return this;
		}

		public Builder timestamp(long timestamp) {
			this.timestamp = timestamp;
			return this;
		}

		public Builder developerMessage(String developerMessage) {
			this.developerMessage = developerMessage;
			return this;
		}
		
		public static Builder newBuilder() {
			return new Builder();
		}
		
		public ErrorArgumentResolvers build() {
			ErrorArgumentResolvers errorArgumentResolvers = new ErrorArgumentResolvers();
			errorArgumentResolvers.setDetail(detail);
			errorArgumentResolvers.setDeveloperMessage(developerMessage);
			errorArgumentResolvers.setStatus(status);
			errorArgumentResolvers.setTimestamp(timestamp);
			errorArgumentResolvers.setTitle(title);
			return errorArgumentResolvers;
		}
		
	}
}