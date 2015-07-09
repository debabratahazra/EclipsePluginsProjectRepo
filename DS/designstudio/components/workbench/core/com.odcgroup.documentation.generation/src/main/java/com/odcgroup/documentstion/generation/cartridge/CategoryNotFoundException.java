package com.odcgroup.documentstion.generation.cartridge;

public class CategoryNotFoundException extends Exception {

	private static final long serialVersionUID = 2667730237098224374L;

	final private String categoryName;
	
	public CategoryNotFoundException(String msg, String categoryName) {
		super(msg);
		this.categoryName = categoryName;
	}

	public String getCategoryName() {
		return categoryName;
	}
	
}
