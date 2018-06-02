package com.adee.datastructure.tree.binary;

/**
 * 模拟数据库的联合索引：
 * 两个字段的联合索引：int类型和varchar类型
 * 
 * @author Administrator
 *
 */
public class TableIndexes implements Comparable<TableIndexes>{
	private Integer index1;
	private String index2;
	
	public int compareTo(TableIndexes that) {
		int ret;
		return (ret = this.getIndex1().compareTo(that.getIndex1())) != 0 ? ret :
							this.getIndex2().compareTo(that.getIndex2());
	}
	

	public Integer getIndex1() {
		return index1;
	}


	public void setIndex1(Integer index1) {
		this.index1 = index1;
	}

	public String getIndex2() {
		return index2;
	}
	public void setIndex2(String index2) {
		this.index2 = index2;
	}
}
