package io.agw.springbootstarter.rabbitmq;

public class RabbitCredentials {

	private String login;
	private String passcode;
	
	public RabbitCredentials(String login, String passcode) {
		super();
		this.login = login;
		this.passcode = passcode;
	}
	
	public String getLogin() {
		return login;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	
	public String getPasscode() {
		return passcode;
	}
	
	public void setPasscode(String passcode) {
		this.passcode = passcode;
	}
	
}
