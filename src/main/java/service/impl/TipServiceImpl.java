package service.impl;

import domain.Forum;
import domain.Tab;
import domain.Tip;
import domain.User;
import mapper.ForumMapper;
import mapper.TabMapper;
import mapper.TipMapper;
import mapper.UserMapper;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import service.TipService;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TipServiceImpl implements TipService {
    @Resource
    private TipMapper tipMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private TabMapper tabMapper;

    @Resource
    private ForumMapper forumMapper;

    @Override
    public String addTip(Tip tip) {
        Logger logger = Logger.getLogger(TipServiceImpl.class);
        logger.info("id为" + tip.getUser_id() + "的用户尝试发表贴子...");
        int result = tipMapper.insTip(tip);
        if (result > 0) {
            return "success";
        } else {
            return "error";
        }
    }

    /**
     * 在获取贴子列表时同时获取其它模型的数据
     *
     * @param tipList List<Tip>
     * @return
     */
    private List<Tip> getTipsSolvedElseModel(List<Tip> tipList) {
        for (int i = 0; i < tipList.size(); i++) {
            User user = userMapper.selUserByUserId(tipList.get(i).getUser_id());
            Tab tab = tabMapper.selTabByTabId(tipList.get(i).getTab_id());
            Forum forum = forumMapper.selForumByForumId(tab.getForum_id());
            tab.setForum(forum);
            // 注入到贴子
            tipList.get(i).setUser(user);
            tipList.get(i).setTab(tab);
        }
        return tipList;
    }

    /**
     * 获取所有贴子信息（不排序）
     *
     * @return
     */
    @Override
    public List<Tip> getAllTip() {
        Logger logger = Logger.getLogger(TipServiceImpl.class);
        logger.info("尝试获取所有贴子信息...");
        List<Tip> tipList = this.getTipsSolvedElseModel(tipMapper.selTipAll());
        if (tipList != null) {
            return tipList;
        }
        return null;
    }

    @Override
    public Tip getTipByTipId(int tip_id) {
        Logger logger = Logger.getLogger(TipServiceImpl.class);
        logger.info("尝试获取ID为" + tip_id + "的贴子信息...");
        Tip tip = tipMapper.selTipByTipId(tip_id);
        if (tip != null) {
            return tip;
        }
        return null;
    }

    @Override
    public String addTipClick(int tip_id) {
        Logger logger = Logger.getLogger(TipServiceImpl.class);
        logger.info("尝试给ID为" + tip_id + "的贴子点击量+1...");
        int result = tipMapper.updTipClick(tip_id);
        if (result > 0) {
            return "success";
        } else {
            return "error";
        }
    }

    @Override
    public String disableTip(int tip_id) {
        Logger logger = Logger.getLogger(TipServiceImpl.class);
        logger.info("尝试逻辑删除id为" + tip_id + "的贴子...");
        int result = tipMapper.updTipIsDeleted(tip_id);
        if (result > 0) {
            return "success";
        } else {
            return "error";
        }
    }

    @Override
    public String enableTip(int tip_id) {
        Logger logger = Logger.getLogger(TipServiceImpl.class);
        logger.info("尝试将id为" + tip_id + "的贴子取消逻辑删除...");
        int result = tipMapper.updTipIsNotDeleted(tip_id);
        if (result > 0) {
            return "success";
        } else {
            return "error";
        }
    }

    @Override
    public String knotTip(int tip_id) {
        Logger logger = Logger.getLogger(TipServiceImpl.class);
        logger.info("【结贴】贴子id：" + tip_id);
        int result = tipMapper.updTipToKnot(tip_id);
        if (result > 0) {
            return "success";
        } else {
            return "error";
        }
    }

    @Override
    public String disNnotTip(int tip_id) {
        Logger logger = Logger.getLogger(TipServiceImpl.class);
        logger.info("【取消结贴】贴子id：" + tip_id);
        int result = tipMapper.updTipToUnKnot(tip_id);
        if (result > 0) {
            return "success";
        } else {
            return "error";
        }
    }

    @Override
    public List<Tip> getAllTipForModifyTimeDesc() {
        Logger logger = Logger.getLogger(TipServiceImpl.class);
        // this.updateAllReplies(); // 更新贴子回复数
        // 获取所有贴子信息
        logger.info("尝试获取所有贴子，并按更新时间倒序排列");
        List<Tip> tipList = this.getTipsSolvedElseModel(tipMapper.selTipAllForModifyTimeDesc());
        if (tipList != null) {
            return tipList;
        }
        return null;
    }

    @Override
    public int countReplyByTipId(int tip_id) {
        Logger logger = Logger.getLogger(ReplyServiceImpl.class);
        logger.info("尝试统计贴子id" + tip_id + "的回复数...");
        int result = tipMapper.selReplyCountedByTipId(tip_id);
        return result;
    }

    /**
     * 刷新贴子更新时间
     *
     * @param tip 贴子对象
     */
    @Override
    public int updateModifyTime(Tip tip) {
        Logger logger = Logger.getLogger(ReplyServiceImpl.class);
        logger.info("尝试刷新贴子id为" + tip.getTip_id() + "的修改时间：" + tip.getTip_modifyTime());
        int result = tipMapper.updModifyTime(tip);
        return result;
    }

    @Override
    public int updateRepliesByTipId(int tip_id) {
        Logger logger = Logger.getLogger(ReplyServiceImpl.class);
        logger.info("尝试更新贴子id为" + tip_id + "的回复数");
        int result = tipMapper.updRepliesByTipId(tip_id);
        return result;
    }

    @Override
    public int updateAllReplies() {
        Logger logger = Logger.getLogger(ReplyServiceImpl.class);
        int result = 0; // 保存更新结果
        logger.info("尝试获取所有贴子的id");
        List<Integer> tipIdsList = tipMapper.selTipIds();
        // 逐个贴子更新
        for (int i = 0; i < tipIdsList.size(); i++) {
            logger.info("尝试更新贴子id为" + tipIdsList.get(i) + "的回复数");
            int tmpResult = tipMapper.updRepliesByTipId(tipIdsList.get(i));
            if (tmpResult > 0) {
                result++;
            }
        }
        return result;
    }

    @Override
    public List<Tip> searchTipByKeyword(String keyword) {
        Logger logger = Logger.getLogger(ReplyServiceImpl.class);
        logger.info("尝试搜索标题、内容包含关键词的贴子：" + keyword);
        List<Tip> tipList = tipMapper.selTipByKeyword(keyword);
        return tipList;
    }

    /**
     * 修改贴子
     * @param tip 贴子对象
     * @return
     */
    @Override
    public String modifyTip(Tip tip) {
        Logger logger = Logger.getLogger(ReplyServiceImpl.class);
        String resultStr = null;
        // 检查贴子是否存在
        Tip tmpTip = tipMapper.selTipByTipId(tip.getTip_id());
        if (tmpTip != null) {
            // 检查修改信息是否相同
            if (tip.getTip_title().equals(tmpTip.getTip_title()) &&
                    tip.getTip_content().equals(tmpTip.getTip_content()) &&
                    (tip.getTab_id() == tmpTip.getTab_id())) {
                resultStr = new String("修改失败：修改后的分类、标题、内容没有变化");
                return resultStr;
            }
            // 开始修改
            logger.info("尝试修改id为" + tip.getTip_id() + "的贴子信息");
            if (tipMapper.updTip(tip) > 0) {
                resultStr = new String("修改成功！");
            } else {
                resultStr = new String("修改失败！");
            }
        }
        return resultStr;
    }

    /**
     * 置顶贴子
     * @param tip_id 贴子id
     * @return
     */
    @Override
    public String doTopTip(int tip_id) {
        Logger logger = Logger.getLogger(TipServiceImpl.class);
        logger.info("尝试置顶id为" + tip_id + "的贴子");
        int result = tipMapper.updTipToTop(tip_id);
        if (result > 0) {
            return "success";
        } else {
            return "error";
        }
    }

    /**
     * 取消置顶
     * @param tip_id 贴子id
     * @return
     */
    @Override
    public String disTopTip(int tip_id) {
        Logger logger = Logger.getLogger(TipServiceImpl.class);
        logger.info("尝试取消置顶id为" + tip_id + "的贴子");
        int result = tipMapper.updTipToUnTop(tip_id);
        if (result > 0) {
            return "success";
        } else {
            return "error";
        }
    }

}
