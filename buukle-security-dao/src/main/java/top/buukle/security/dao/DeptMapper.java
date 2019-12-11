package top.buukle.security.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.buukle.security.entity.Dept;
import top.buukle.security.entity.DeptExample;
import top.buukle.security.entity.vo.DeptSessionVo;

@Mapper
public interface DeptMapper {
    /**
     *
     * @mbg.generated Wed Dec 11 14:13:46 CST 2019
     */
    long countByExample(DeptExample example);

    /**
     *
     * @mbg.generated Wed Dec 11 14:13:46 CST 2019
     */
    int deleteByExample(DeptExample example);

    /**
     *
     * @mbg.generated Wed Dec 11 14:13:46 CST 2019
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbg.generated Wed Dec 11 14:13:46 CST 2019
     */
    int insert(Dept record);

    /**
     *
     * @mbg.generated Wed Dec 11 14:13:46 CST 2019
     */
    int insertSelective(Dept record);

    /**
     *
     * @mbg.generated Wed Dec 11 14:13:46 CST 2019
     */
    List<Dept> selectByExample(DeptExample example);

    /**
     *
     * @mbg.generated Wed Dec 11 14:13:46 CST 2019
     */
    Dept selectByPrimaryKey(Integer id);

    /**
     *
     * @mbg.generated Wed Dec 11 14:13:46 CST 2019
     */
    int updateByExampleSelective(@Param("record") Dept record, @Param("example") DeptExample example);

    /**
     *
     * @mbg.generated Wed Dec 11 14:13:46 CST 2019
     */
    int updateByExample(@Param("record") Dept record, @Param("example") DeptExample example);

    /**
     *
     * @mbg.generated Wed Dec 11 14:13:46 CST 2019
     */
    int updateByPrimaryKeySelective(Dept record);

    /**
     *
     * @mbg.generated Wed Dec 11 14:13:46 CST 2019
     */
    int updateByPrimaryKey(Dept record);


    DeptSessionVo selectUserDept(String userId);
}