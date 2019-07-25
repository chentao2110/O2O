package com.ctao.O2O.dao;

import com.ctao.O2O.Exceptions.HeadLineOperationException;
import com.ctao.O2O.entity.HeadLine;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HeadLineDao {
    /**
     * 添加头条信息
     * @param headLine headLine
     * @return 影响行数
     * @throws HeadLineOperationException 异常
     */
    int addHeadLine(HeadLine headLine) throws HeadLineOperationException;

    /**
     * 更改头条信息
     * @param headLine 头条
     * @return 影响行数
     * @throws HeadLineOperationException 异常
     */
    int modifyHeadLine(HeadLine headLine) throws HeadLineOperationException;

    /**
     * 根据条件查询符合条件的头条
     * @param headLine
     * @return
     * @throws HeadLineOperationException
     */
    List<HeadLine> queryHeadLineList(HeadLine headLine) throws HeadLineOperationException;

    /**
     * 根据id查询HeadLine
     * @param lineId lineId
     * @return HeadLine
     * @throws HeadLineOperationException 操作异常
     */
    HeadLine getHeadLineByLineId(long lineId) throws  HeadLineOperationException;

}
