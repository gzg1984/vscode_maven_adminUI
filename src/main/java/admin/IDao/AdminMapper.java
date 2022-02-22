package admin.IDao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import admin.domain.Admin;

public interface AdminMapper {
    int deleteByPrimaryKey(Integer adminId);

    int insert(Admin record);

    int insertSelective(Admin record);

    Admin selectByPrimaryKey(Integer adminId);

    int updateByPrimaryKeySelective(Admin record);

    int updateByPrimaryKey(Admin record);
    
    List<Admin> getAdminVOList(@Param("username")String username,@Param("password")String md5_password);

    List<Admin> getAllAdmin();

}