package member.model;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

public class MemberBean {
	private int mnum;
	
	@NotEmpty(message="아이디를 입력하세요.")
	private String id;
	
	@NotEmpty(message = "비밀번호를 입력하세요.")
    @Size(min = 3, max = 10, message = "비밀번호는 3자에서 10자 사이여야 합니다.")
	private String password;
	
	@NotEmpty(message = "이름을 입력하세요.")
    private String name;

    @NotEmpty(message = "성별을 선택하세요.")
	private String gender;
    
    @NotBlank(message = "가수를 선택하세요.")
	private String singer;
    @NotBlank(message = "노래를 선택하세요.")
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
