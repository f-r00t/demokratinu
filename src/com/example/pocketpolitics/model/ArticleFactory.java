package com.example.pocketpolitics.model;

public class ArticleFactory {
	public static Article getArticle(){
		Article article = new Article();
		article.setTitle("Test");
		return article;
	}
}
