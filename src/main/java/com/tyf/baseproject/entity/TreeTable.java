package com.tyf.baseproject.entity;

public class TreeTable {

	public String id;
	public String pId;
	public String name;
	public String[] td;
	public String icon;

	public TreeTable() {
		super();
	}

	public TreeTable(String id, String pId, String name, String[] td) {
		super();
		this.id = id;
		this.pId = pId;
		this.name = name;
		this.td = td;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String[] getTd() {
		return td;
	}

	public void setTd(String[] td) {
		this.td = td;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
}
