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
import java.util.ArrayList;
import java.util.Date;
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

    // 日志 2020-09-24 优化 不用每个方法都声明Logger
    private Logger logger = Logger.getLogger(TipServiceImpl.class);

    /**
     * 发贴
     * v1.1 2020-03-04 10:16 DAO改为使用insTipV2
     *
     * @param tip 贴子对象
     * @return
     */
    @Override
    public String addTip(Tip tip) {
        Logger logger = Logger.getLogger(TipServiceImpl.class);
        logger.info("id为" + tip.getUser_id() + "的用户尝试发表贴子...");
        int result = tipMapper.insTipV2(tip);
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

    /**
     * 根据贴子id获取贴子信息
     * v1.1 2020-03-14 23:10 增加获取发贴人信息
     *
     * @param tip_id 贴子id
     * @return
     */
    @Override
    public Tip getTipByTipId(int tip_id) {
        Logger logger = Logger.getLogger(TipServiceImpl.class);
        logger.info("尝试获取ID为" + tip_id + "的贴子信息...");
        Tip tip = tipMapper.selTipByTipId(tip_id);

        if (tip != null) {
            // 获取发贴人信息
            User tipOwner = userMapper.selUserByUserId(tip.getUser_id());
            if (tipOwner != null) {
                tip.setUser(tipOwner);
            }
        }

        // 判空
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

    /**
     * 【已弃用，改为使用getMainPageTips()获取置顶贴和未置顶贴 2020-02-27 16:43】
     * 获取所有贴子
     *
     * @return
     */
    @Deprecated
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

    /**
     * 【首页搜索】根据关键词搜索贴子标题和内容
     * 2020-03-14 21:23
     *
     * @param keyword 关键词
     * @return
     */
    @Override
    public List<Tip> searchTipByKeyword(String keyword) {
        Logger logger = Logger.getLogger(ReplyServiceImpl.class);
        logger.info("尝试搜索标题、内容包含关键词的贴子：" + keyword);

        List<Tip> finalTipList = new ArrayList<>(); // 保存最终返回的贴子数组
        // 保存搜索得到的贴子数组
        List<Tip> keywordTipList = new ArrayList<>();
        // 先获取置顶贴子
        List<Tip> topTipList = this.getTipsSolvedElseModel(tipMapper.selAllTopTipForTopTimeDesc());
        // 关键词判空
        if (keyword == null || keyword.equals("")) {
            // 所有未置顶贴子
            keywordTipList = this.getTipsSolvedElseModel(tipMapper.selAllUnTopTipForModifyTimeDesc());
        } else {
            // 关键词不为空时搜索到的贴子
            keywordTipList = this.getTipsSolvedElseModel(tipMapper.selTipByKeyword(keyword));
        }

        // 放入最终返回的贴子数组
        finalTipList.addAll(topTipList);
        finalTipList.addAll(keywordTipList);
        // 判空
        if (finalTipList != null) {
            return finalTipList;
        }
        return null;
    }

    /**
     * 修改贴子
     *
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
     *
     * @param tip_id 贴子id
     * @return
     */
    @Override
    public String doTopTip(int tip_id) {
        Logger logger = Logger.getLogger(TipServiceImpl.class);
        logger.info("尝试置顶id为" + tip_id + "的贴子");
        int result = tipMapper.updTipToTop(tip_id);
        if (result > 0) {
            // 更新置顶时间
            Date date = new Date(); // 获取当前时间
            Tip tmpTip = new Tip(); // 贴子对象
            tmpTip.setTip_id(tip_id); // 保存贴子id
            tmpTip.setTip_topTime(date); // 保存置顶时间
            // 调用方法更新置顶时间
            int updToptimeResult = this.updateTopTime(tmpTip);
            if (updToptimeResult > 0) {
                return "success";
            } else {
                System.err.println("更新置顶时间失败！");
            }
            return "success";
        } else {
            return "error";
        }
    }

    /**
     * 取消置顶
     *
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

    /**
     * 置顶时间刷新
     * 2020-02-27 10:25
     *
     * @param tip 贴子对象
     * @return
     */
    @Override
    public int updateTopTime(Tip tip) {
        Logger logger = Logger.getLogger(ReplyServiceImpl.class);
        logger.info("尝试刷新贴子id为" + tip.getTip_id() + "的置顶时间：" + tip.getTip_modifyTime());
        int result = tipMapper.updTopTime(tip);
        return result;
    }

    /**
     * 主页显示贴子用
     * 2020-02-27 16:33
     *
     * @return
     */
    @Override
    public List<Tip> getMainPageTips() {
        Logger logger = Logger.getLogger(TipServiceImpl.class);
        logger.info("处理主页显示的贴子");
        List<Tip> finalTipList = new ArrayList<>(); // 保存最终返回的贴子
        // 先获取置顶贴子
        List<Tip> topTipList = this.getTipsSolvedElseModel(tipMapper.selAllTopTipForTopTimeDesc());
        // 然后是未置顶的贴子
        List<Tip> unTopTipList = this.getTipsSolvedElseModel(tipMapper.selAllUnTopTipForModifyTimeDesc());
        // 放入最终返回的贴子数组
        finalTipList.addAll(topTipList);
        finalTipList.addAll(unTopTipList);
        // 判空
        if (finalTipList != null) {
            return finalTipList;
        }
        return null;
    }

    /**
     * 模糊查询贴子 2020-09-24
     * 2020-09-24 修复查询到的贴子没有其它模型数据
     */
    @Override
    public List<Tip> searchTipFuzzy(String keyword) {
        logger.info("尝试根据关键词搜索贴子");
        // 调用数据库
        List<Tip> tipList = this.getTipsSolvedElseModel(tipMapper.selTipFuzzy(keyword));
        if (tipList != null) {
            return tipList;
        }
        return null;
    }

}
