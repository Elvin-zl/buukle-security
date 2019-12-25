package top.buukle.security.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.buukle.security.entity.Area;
import top.buukle.security.entity.AreaExample;

@Mapper
public interface AreaMapper {
    /**
     *
     * @mbg.generated Wed Dec 25 20:29:15 CST 2019
     */
    long countByExample(AreaExample example);

    /**
     *
     * @mbg.generated Wed Dec 25 20:29:15 CST 2019
     */
    int deleteByExample(AreaExample example);

    /**
     *
     * @mbg.generated Wed Dec 25 20:29:15 CST 2019
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbg.generated Wed Dec 25 20:29:15 CST 2019
     */
    int insert(Area record);

    /**
     *
     * @mbg.generated Wed Dec 25 20:29:15 CST 2019
     */
    int insertSelective(Area record);

    /**
     *
     * @mbg.generated Wed Dec 25 20:29:15 CST 2019
     */
    List<Area> selectByExample(AreaExample example);

    /**
     *
     * @mbg.generated Wed Dec 25 20:29:15 CST 2019
     */
    Area selectByPrimaryKey(Integer id);

    /**
     *
     * @mbg.generated Wed Dec 25 20:29:15 CST 2019
     */
    int updateByExampleSelective(@Param("record") Area record, @Param("example") AreaExample example);

    /**
     *
     * @mbg.generated Wed Dec 25 20:29:15 CST 2019
     */
    int updateByExample(@Param("record") Area record, @Param("example") AreaExample example);

    /**
     *
     * @mbg.generated Wed Dec 25 20:29:15 CST 2019
     */
    int updateByPrimaryKeySelective(Area record);

    /**
     *
     * @mbg.generated Wed Dec 25 20:29:15 CST 2019
     */
    int updateByPrimaryKey(Area record);
}