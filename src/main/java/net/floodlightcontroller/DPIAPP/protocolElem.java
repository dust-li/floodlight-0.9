package net.floodlightcontroller.DPIAPP;

public class protocolElem {
	private String name;
	private int weight;
	private String pattern;
	public String getPattern(){
		return this.pattern;
	}
	public void setElem(String name,int weight,String pattern){
		this.name=name;
		this.weight=weight;
		this.pattern=pattern;
	}
	public protocolElem(String name,int weight,String pattern){
		this.name=name;
		this.weight=weight;
		this.pattern=pattern;
	}
	public int getWeight(){
		return this.weight;
	}
	public void setWeight(int weight){
		this.weight=weight;
	}
	public String getName(){
		return this.name.toUpperCase();
	}
}
