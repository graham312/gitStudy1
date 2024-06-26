package member.model;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

public class MemberBean {
	private int mnum;
	
	@NotEmpty(message="���̵� �Է��ϼ���.")
	private String id;
	
	@NotEmpty(message = "��й�ȣ�� �Է��ϼ���.")
    @Size(min = 3, max = 10, message = "��й�ȣ�� 3�ڿ��� 10�� ���̿��� �մϴ�.")
	private String password;
	
	@NotEmpty(message = "�̸��� �Է��ϼ���.")
    private String name;

    @NotEmpty(message = "������ �����ϼ���.")
	private String gender;
    
    @NotBlank(message = "������ �����ϼ���.")
	private String singer;
    @NotBlank(message = "�뷡�� �����ϼ���.")
	private String song;
	
	
	public int getMnum() {
		return mnum;
	}
	public void setMnum(int mnum) {
		this.mnum = mnum;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getSinger() {
		return singer;
	}
	public void setSinger(String singer) {
		this.singer = singer;
	}
	public String getSong() {
		return song;
	}
	public void setSong(String song) {
		this.song = song;
	}
	
	
	
	
	
	
	
}
