package com.gree.test.algorithm;

import freemarker.template.utility.StringUtil;
import lombok.Data;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Create by yang_zzu on 2020/5/19 on 21:13
 */
public class DiGui {

    @Data
    static class Menu {
        private String id;
        private String name;
        private String pid;
    }

    /**
     * 获取某个节点的所有子节点
     * @param cid
     * @param treeList
     * @return
     */
    public static List<Menu> getChildTreeById(Integer cid, List<Menu> treeList){
        List<Menu> list = new ArrayList<>();
        if(null != treeList){
            for (Menu d : treeList) {
                if(null != cid){
                    if (cid.equals(Integer.valueOf(d.getPid()))) {
                        getChildTreeById(Integer.valueOf(d.getId()), treeList);
                        list.add(d);
                    }
                }
            }
        }
        return list;
    }

    /**
     * 1. 先找出没有父节点的数据
     * 2. 依次对这些父节点进行遍历，查找子节点（递归）
     */

    /**
     * 没有子节点的叶子节点
     * @param menuList
     * @return
     */
    public static Queue<Menu> leavesNode(List<Menu> menuList) {
        Queue<Menu> menuQueue = new LinkedList<>();
        for (Menu menu : menuList) {
            //获得该节点的id
            String id = menu.getId();
            //
            List<Menu> list = new ArrayList<>();
            for (Menu menu1 : menuList) {
                if ( id.equals(menu1.getPid())) {
                    //说明该节点不是根节点
                    list.add(menu1);
                }

            }
            if (list.size() > 0) {
                //说明有子节点
            } else {
                menuQueue.add(menu);
            }
        }

        return menuQueue;
    }

    /**
     * 获得的最终结果 缺少一个 }
     * 尝试了很多次，没有成功
     * @param menuList
     * @param menus
     * @return
     */
    public static StringBuffer stringResult(List<Menu> menuList, Queue<Menu> menus, StringBuffer stringBuffer) {
        //如果传过来的队列不是空的
        if (menus.size() > 0) {
            stringBuffer.append("{");
        }
        while (menus.size() > 0) {
            Menu poll = menus.poll();
            //节点名字，放到字符串中
            stringBuffer.append(poll.getName());
            //获得当前节点的子节点
            Queue<Menu> menus1 = childNode(menuList, poll.getId());

            if (menus1.size() > 0) {
//                resultString.append("{");
                // 队列大于0，说名有子节点
                StringBuffer stringBuffer_  = stringResult(menuList,menus1,stringBuffer);
                if (menus.size() > 0) {
                    stringBuffer.append("}、");
                }else{
                    /*if(menus1.size() == 0){
                        resultString.append("}");
                    }*/
                }

            }else {
                //当前节点无子节点
                if (menus.size() > 0) {
                    stringBuffer.append("、");
                } else {
                    stringBuffer.append("}");
                }
            }
        }
        return stringBuffer;
    }

    /**
     * 查找根节点
     * @param menuList
     * @return
     */
    public static Queue<Menu> rootNode(List<Menu> menuList) {
        Queue<Menu> menuQueue = new LinkedList<>();
        for (Menu menu : menuList) {
            //获得该节点的 父id
            String pid = menu.getPid();
            //
            List<Menu> list = new ArrayList<>();
            for (Menu menu1 : menuList) {
                if ( pid.equals(menu1.getId())) {
                    //说明该节点不是根节点
                    list.add(menu1);
                }
            }
            if (list.size() > 0) {
                //说明有子节点
            } else {
                menuQueue.add(menu);
            }
        }
        return menuQueue;
    }

    /**
     * 当前节点的子节点
     * @param menuList
     * @param id
     * @return
     */
    public static Queue<Menu> childNode(List<Menu> menuList, String id) {

        Queue<Menu> menus = new LinkedList<>();
        for (Menu menu : menuList) {
            if (menu.getPid().equals(id)) {
                menus.add(menu);
            }
        }
        return menus;
    }


    /**
     *
     * @param menuList 节点集合
     * @param menuQueue 当前节点的所有子节点
     * @param stringBuffer 存储结果
     * @return
     */
    public static StringBuffer nodeString(List<Menu> menuList, Queue<Menu> menuQueue, StringBuffer stringBuffer) {

        //节点集合,父节点后面的额
        if (menuQueue.size()>0) {
            stringBuffer.append("{");
        }
        while (menuQueue.size() > 0) {
            //获得当前节点
            Menu poll = menuQueue.poll();

            //获得当前节点的所有子节点
            Queue<Menu> menus = childNode(menuList, poll.getId());
            if (menus.size() > 0) {
                //子节点1   子节点>1
                stringBuffer.append(poll.getName());

                StringBuffer stringBuffer1 = nodeString(menuList, menus, stringBuffer);
//                System.out.println(menus.size());
                stringBuffer.append("}");
            } else {
                //说明父节点的，所有子节点都遍历完了
                if (menuQueue.size() == 0) {
                    stringBuffer.append(poll.getName());
                    stringBuffer.append("}");

                } else {
                    //当前节点没有子节点
                    stringBuffer.append(poll.getName());
                    stringBuffer.append("、");
                }

            }
        }

        return stringBuffer;

    }

    //子节点
    static List<Menu> childMenu = new ArrayList<Menu>();
    /**
     * 获取某个父节点下面的所有子节点
     * @param menuList
     * @param pid
     * @return
     */
    public static List<Menu> treeMenuList(List<Menu> menuList, int pid) {
        for (Menu mu : menuList) {
            //遍历出父id等于参数的id，add进子节点集合
            if (Integer.valueOf(mu.getPid()) == pid) {
                //递归遍历下一级
                treeMenuList(menuList, Integer.valueOf(mu.getId()));
                childMenu.add(mu);
            }
        }
        return childMenu;
    }

    /**
     * {目录{目录1{目录1.1、目录1.2}、目录2{目录2.1、目录2.2{目录2.2.1}}}、目录A{目录A.1、目录A.2}}
     */
    public static void main(String args[]) {
        List<Menu> menuList = new ArrayList<Menu>();
        //第一个根节点数据
        Menu mu = new Menu();
        mu.setId("1");
        mu.setName("目录");
        mu.setPid("0");

        Menu mu1 = new Menu();
        mu1.setId("2");
        mu1.setName("目录1");
        mu1.setPid("1");

        Menu mu2 = new Menu();
        mu2.setId("3");
        mu2.setName("目录1.1");
        mu2.setPid("2");

        Menu mu3 = new Menu();
        mu3.setId("4");
        mu3.setName("目录1.2");
        mu3.setPid("2");

        Menu mu4 = new Menu();
        mu4.setId("5");
        mu4.setName("目录2");
        mu4.setPid("1");

        Menu mu5 = new Menu();
        mu5.setId("6");
        mu5.setName("目录2.1");
        mu5.setPid("5");

        Menu mu6 = new Menu();
        mu6.setId("7");
        mu6.setName("目录2.2");
        mu6.setPid("5");

        Menu mu7 = new Menu();
        mu7.setId("8");
        mu7.setName("目录2.2.1");
        mu7.setPid("7");

        //另外一个根节点数据
        Menu mu8 = new Menu();
        mu8.setId("9");
        mu8.setName("目录A");
        mu8.setPid("100");

        Menu mu9 = new Menu();
        mu9.setId("10");
        mu9.setName("目录A.1");
        mu9.setPid("9");

        Menu mu10 = new Menu();
        mu10.setId("11");
        mu10.setName("目录A.2");
        mu10.setPid("9");

        menuList.add(mu);
        menuList.add(mu1);
        menuList.add(mu2);
        menuList.add(mu3);
        menuList.add(mu4);
        menuList.add(mu5);
        menuList.add(mu6);
        menuList.add(mu7);
        menuList.add(mu8);
        menuList.add(mu9);
        menuList.add(mu10);

        //获得所有的根节点
        Queue<Menu> menus = rootNode(menuList);
        System.out.println(menus.toString());

        //存储树返回的结果
        StringBuffer stringBuffer = new StringBuffer();

        StringBuffer s = nodeString(menuList, menus, stringBuffer );
        System.out.println(s);

        //获得字符串中 }}} 这个并且后面还有元素的 将最后一个 } 变为 、
        String s1 = s.toString();
        //用于替换字符串指定索引的字符
        StringBuilder sb = new StringBuilder(s1);
        String[] split = StringUtil.split(s1, '}');
        for (String s2 : split) {
//            System.out.println(s2);
            if (!s2.isEmpty()) {
                int i = s1.indexOf(s2);
                if (i > 0) {
                    //替换多个 }}} 的最后一个字符，[1,2)  包含左不包含右
                    sb.replace(i - 1, i , "、");
                }
                System.out.println(i);
            }
        }
        System.out.println(sb);

        while (menus.size() > 0) {
            //出队列
            Menu poll = menus.poll();
//            System.out.println(poll.toString());
            //获得当前节点的子节点
            Queue<Menu> menus1 = childNode(menuList, poll.getId());

        }

    }

}
