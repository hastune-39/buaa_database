--用户添加
insert into LSP.dbo.users
(login_name, userName, pass_word, sex, address, profile_picture, signature)
values
('1776331069','初音未来','sqlserver', '?', '日本 東京都', 'static/profilePicture/1.jpg', 'miku! miku!'),
('1776331000', 'DM袋喵','sqlserver', '?', '中国 上海', 'static/profilePicture/2.jpg', 'Twitter@matchachWeibo@抹茶專門店AM
Glad to see u and always be happy to make friends with u~ thx to all the u friendly to me!!(　´∀｀)
(ps.If u want chat with me,use en)...'),
('1776331001', '米山舞','sqlserver', '?', '日本 東京都', 'static/profilePicture/3.jpg', '这个人很懒，什么也没写'),
('1776331002', 'Rella','sqlserver', '?', '日本 東京都', 'static/profilePicture/4.jpg', '这个人很懒，什么也没写'),
('1776331003', 'Roha','sqlserver', '?', '日本 東京都', 'static/profilePicture/5.jpg', '这个人很懒，什么也没写'),
('1776331004', 'Relua','sqlserver', '?', '日本 東京都', 'static/profilePicture/6.jpg', '这个人很懒，什么也没写'),
('1776331005', '其它','sqlserver', '?', '日本 東京都', 'static/profilePicture/7.jpg', '这个人很懒，什么也没写')
go

--画师
insert into LSP.dbo.painters
(painter_id)
values
(2),
(3),
(4),
(5),
(6),
(7)
go

--图片
insert into LSP.dbo.pictures
(picture_id, painter_id, picture_address, title)
values
(1,7,'static/img/others/0.jpg','no'),
(2,7,'static/img/others/12.jpg','no'),
(3,7,'static/img/others/16.jpg','no'),
(4,2,'static/img/DM袋喵/1.jpg','小姐姐'),
(5,2,'static/img/DM袋喵/2.jpg','电锯人'),
(6,2,'static/img/DM袋喵/3.jpg','深渊'),
(7,2,'static/img/DM袋喵/4.jpg','furry'),
(8,2,'static/img/DM袋喵/5.jpg','咒术回战'),
(9,2,'static/img/DM袋喵/6.jpg','给舰长的头像~'),
(10,2,'static/img/DM袋喵/7.jpg','阿提亚'),
(11,3,'static/img/米山舞/1.jpg','no'),
(12,3,'static/img/米山舞/2.jpg','no'),
(13,3,'static/img/米山舞/3.jpg','no'),
(14,4,'static/img/Rella/1.jpg','no'),
(15,4,'static/img/Rella/2.jpg','no'),
(16,4,'static/img/Rella/3.jpg','no'),
(17,5,'static/img/Roha/1.jpg','no'),
(18,5,'static/img/Roha/2.jpg','no'),
(19,6,'static/img/Rolua/1.jpg','no'),
(20,6,'static/img/Rolua/2.jpg','no'),
(21,6,'static/img/Rolua/3.jpg','no'),
(22,6,'static/img/Rolua/4.jpg','no'),
(23,6,'static/img/Rolua/5.jpg','no'),
(24,6,'static/img/Rolua/6.jpg','no')
go

--关注
insert into LSP.dbo.follows
(follower_id, painter_id)
values
(1,2),
(1,3),
(1,4),
(1,5),
(1,6),
(1,7)
go

--关键字
insert into LSP.dbo.keywords
values
(4,'帅气'),
(4,'仰视'),
(4,'DM袋喵'),
(5,'天使'),
(5,'DM袋喵'),
(6,'崩坏三'),
(6,'深渊法师'),
(6,'DM袋喵'),
(7,'furry'),
(7,'温馨'),
(7,'ghs'),
(7,'DM袋喵'),
(8,'咒术回战'),
(8,'五条悟'),
(8,'无量空处'),
(8,'DM袋喵'),
(9,'恼怒'),
(9,'漂亮'),
(9,'DM袋喵'),
(10,'奶酥atiya'),
(10,'DM袋喵')
go

--评论
insert into LSP.dbo.comments (commentator_id,picture_id,content)
values
(2,21,'《原神》关于钟离相关问题的说明 亲爱的旅行者们，你们好。'),
(3,21,'最近，我们陆续收到旅行者对钟离相关问题的反馈。最早从线下测试开始，开发组就持续高度关注着钟离角色的表现，我们对大家的反馈进行了分析和整理，并进行了多轮内部讨论。'),
(4,21,'十分抱歉，由于我们回应较晚，给大家带来了不好的感受。其实在钟离角色正式上线后，不管是大家在社区的相关讨论，还是游戏内获取、培养以及实际使用钟离的情况，我们都在从各个维度持续关注和跟踪着这件事。'),
(5,21,'在此，我们也希望和大家进行沟通，和大家说一说我们关于钟离这个角色的想法。'),
(6,21,'在设计时，我们希望能保证基础游玩体验和玩法策略的多样化。
在钟离的角色设计定位上也是如此。他是一个偏辅助定位的角色，辅助方向以庇护队伍中的其他角色为核心。基于这一点，我们将他最核心的能力，定位在了制造护盾、控制敌人这两点上'),
(7,21,'所有针对关卡、大世界、系统乃至角色本身的调整，我们都会基于角色战斗数据、用户反馈和角色设定综合评估的结果，谨慎判断并合理制定方案。目前，关于钟离的深入分析已在进行中。在未来的版本中，我们还会根据分析结果持续评估和打磨，让每一位旅行者喜爱的角色都有发挥能力的舞台，还请各位旅行者继续保持耐心等待。')
go