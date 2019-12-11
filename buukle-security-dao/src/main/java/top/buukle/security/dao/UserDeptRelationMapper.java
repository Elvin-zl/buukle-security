package top.buukle.security.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.buukle.security.entity.UserDeptRelation;
import top.buukle.security.entity.UserDeptRelationExample;

@Mapper
public interface UserDeptRelationMapper {
    /**
     *
     * @mbg.generated Wed Dec 11 14:13:52 CST 2019
     */
    long countByExample(UserDeptRelationExample example);

    /**
     *
     * @mbg.generated Wed Dec 11 14:13:52 CST 2019
     */
    int deleteByExample(UserDeptRelationExample example);

    /**
     *
     * @mbg.generated Wed Dec 11 14:13:52 CST 2019
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbg.generated Wed Dec 11 14:13:52 CST 2019
     */
    int insert(UserDeptRelation record);

    /**
     *
     * @mbg.generated Wed Dec 11 14:13:52 CST 2019
     */
    int insertSelective(UserDeptRelation record);

    /**
     *
     * @mbg.generated Wed Dec 11 14:13:52 CST 2019
     */
    List<UserDeptRelation> selectByExample(UserDeptRelationExample example);

    /**
     *
     * @mbg.generated Wed Dec 11 14:13:52 CST 2019
     */
    UserDeptRelation selectByPrimaryKey(Integer id);

    /**
     *
     * @mbg.generated Wed Dec 11 14:13:52 CST 2019
     */
    int updateByExampleSelective(@Param("record") UserDeptRelation record, @Param("example") UserDeptRelationExample example);

    /**
     *
     * @mbg.generated Wed Dec 11 14:13:52 CST 2019
     */
    int updateByExample(@Param("record") UserDeptRelation record, @Param("example") UserDeptRelationExample example);

    /**
     *
     * @mbg.generated Wed Dec 11 14:13:52 CST 2019
     */
    int updateByPrimaryKeySelective(UserDeptRelation record);

    /**
     *
     * @mbg.generated Wed Dec 11 14:13:52 CST 2019
     */
    int updateByPrimaryKey(UserDeptRelation record);
}