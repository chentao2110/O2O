package com.ctao.O2O.service.impl;

import com.ctao.O2O.Exceptions.HeadLineOperationException;
import com.ctao.O2O.dao.HeadLineDao;
import com.ctao.O2O.dto.HeadLineExecution;
import com.ctao.O2O.entity.HeadLine;
import com.ctao.O2O.enums.HeadLineStateEnum;
import com.ctao.O2O.service.HeadLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HeadLineServiceImpl implements HeadLineService {
    @Autowired
    private HeadLineDao headLineDao;
    @Override
    public HeadLineExecution addHeadLine(HeadLine headLine) {
       return null;
    }

    @Override
    public HeadLineExecution modifyHeadLine(HeadLine headLine) {
        return null;
    }

    @Override
    public HeadLineExecution getHeadLineByLineId(long lineId) {
        return null;
    }

    @Override
    public HeadLineExecution queryHeadLineList(HeadLine headLine) {
        HeadLineExecution  he = new HeadLineExecution();
        List<HeadLine> headLineList = headLineDao.queryHeadLineList(headLine);
        if (headLineList == null) {
            throw new HeadLineOperationException(HeadLineStateEnum.EMPTY_LIST.getStateInfo());
        }else {

            he.setHeadLineList(headLineList);
            he.setState(HeadLineStateEnum.SUCCESS.getState());
            he.setStateInfo(HeadLineStateEnum.SUCCESS.getStateInfo());
        }
        return he;
    }
}
