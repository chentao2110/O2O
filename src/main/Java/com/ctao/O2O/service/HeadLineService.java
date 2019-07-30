package com.ctao.O2O.service;

import com.ctao.O2O.dto.HeadLineExecution;
import com.ctao.O2O.entity.HeadLine;

public interface HeadLineService {
    /**
     * 添加头条信息
     * @param headLine headLine
     * @return 结果数据
     */
    HeadLineExecution addHeadLine(HeadLine headLine);

    /**
     * 更改头条信息
     * @param headLine headline
     * @return 结果数据
     */
    HeadLineExecution modifyHeadLine(HeadLine headLine);

    /**
     * 通过头条id号查找头条
     * @param lineId 头条id号
     * @return 结果数据
     */
    HeadLineExecution getHeadLineByLineId(long lineId);

    /**
     * 通过条件获取头条列表
     * @param headLine headLine
     * @return 结果数据
     */
    HeadLineExecution queryHeadLineList(HeadLine headLine);





}
