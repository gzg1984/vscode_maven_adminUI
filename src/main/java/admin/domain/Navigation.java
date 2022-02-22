package admin.domain;

import java.util.List;

/**
 * 菜单表pojo
 * @author david
 *
 */
public class Navigation {

	/**
	 * 主键
	 */
	private int navigationId;
	/**
	 * 父级菜单id
	 */
	private int parentId;
	/**
	 * 菜单类型
	 */
	private String navType;
	/**
	 * 菜单标题
	 */
	private String title;
	/**
	 * 菜单副标题或缩写
	 */
	private String shortTitle;
	/**
	 * 菜单图标的css
	 */
	private String iconClass;
	/**
	 * 菜单url
	 */
	private String linkUrl;
	/**
	 * 菜单英文name
	 */
	private String navName;
	/**
	 * 删除标识
	 */
	private int isUse;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 菜单包含的操作类型,用英文逗号隔开   例如:Show,Access,Add,Edit,Delete
	 * @see com.genesisdo.travel.base.enums.ActionType  操作类型枚举
	 */
	private String actionType;
	/**
	 * 当前菜单的子菜单
	 */
	private List<Navigation> children;
	public int getNavigationId() {
		return navigationId;
	}
	public void setNavigationId(int navigationId) {
		this.navigationId = navigationId;
	}
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	public String getNavType() {
		return navType;
	}
	public void setNavType(String navType) {
		this.navType = navType;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getShortTitle() {
		return shortTitle;
	}
	public void setShortTitle(String shortTitle) {
		this.shortTitle = shortTitle;
	}
	public String getIconClass() {
		return iconClass;
	}
	public void setIconClass(String iconClass) {
		this.iconClass = iconClass;
	}
	public String getLinkUrl() {
		return linkUrl;
	}
	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}
	public String getNavName() {
		return navName;
	}
	public void setNavName(String navName) {
		this.navName = navName;
	}
	public int getIsUse() {
		return isUse;
	}
	public void setIsUse(int isUse) {
		this.isUse = isUse;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getActionType() {
		return actionType;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	public List<Navigation> getChildren() {
		return children;
	}
	public void setChildren(List<Navigation> children) {
		this.children = children;
	}
	
	
}
