package springtest.cdplay;

import org.springframework.stereotype.Component;

@Component
public class SgtPeppers implements CompactDisc {

	private String title = "mucis hangkong";
	private String author = "yaoyz";
	
	public void play() {
		System.out.println(title+" by "+author);
	}

}
