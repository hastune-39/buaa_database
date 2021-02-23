USE master
GO
IF NOT EXISTS (
   SELECT name
   FROM sys.databases
   WHERE name = N'LSP'
)
CREATE DATABASE [LSP]
GO

USE [LSP]
-- Create a new table called 'Customers' in schema 'dbo'
-- Drop the table if it already exists
if OBJECT_ID('collections','U') is not null
	drop table collections
go

if OBJECT_ID('private_letters','U') is not null
	drop table private_letters
go
if OBJECT_ID('supports','U') is not null
	drop table supports
go

if OBJECT_ID('browses','U') is not null
	drop table browses
go

if OBJECT_ID('favorites_pictures','U') is not null
	drop table favorites_pictures
go

if OBJECT_ID('favorites','U') is not null
	drop table favorites
go

if OBJECT_ID('comment_likes','U') is not null
	drop table comment_likes
go

if OBJECT_ID('comments','U') is not null
	drop table comments
go

if OBJECT_ID('pictures_sets_contents','U') is not null
	drop table pictures_sets_contents
go

if OBJECT_ID('pictures_sets','U') is not null
	drop table pictures_sets
go

if OBJECT_ID('keywords','U') is not null
	drop table keywords
go

if OBJECT_ID('pictures','U') is not null
	drop table pictures
go

if OBJECT_ID('follows','U') is not null
	drop table follows 
go

if OBJECT_ID('painters','U') is not null
	drop table painters
go

IF OBJECT_ID('users', 'U') IS NOT NULL
drop table users

GO
-- Create the table in the specified schema
CREATE TABLE users
(
   userID int identity(1,1) primary key,
   login_name varchar(16) NOT NULL UNIQUE,
   userName varchar(16) DEFAULT 'û������(�ѩn��)',
   pass_word varchar(16) NOT NULL,
   sex varchar(4) default '?' check(sex = '��' or sex='Ů' or sex='?'),
   address varchar(20) default '����',
   profile_picture varchar(128) DEFAULT 'static/profilePicture/2.jpg',
   signature text NULL default '����˺�����ʲô��ûд...'
);
go
 

create table painters(
	painter_id int PRIMARY KEY REFERENCES users(userID)  ,
	registeTime datetime default getdate(),
);
go


create table follows(
	follower_id int REFERENCES users(userID)  ,
	painter_id int REFERENCES painters(painter_id)  ,
	followTime datetime default getdate(),
);
go


create table pictures(
	picture_id int PRIMARY KEY,
	painter_id int REFERENCES painters(painter_id)  ,
	picture_address varchar(128) NOT NULL,
	uploadTime datetime default getdate(),
	title varchar(16) NOT NULL
);
go


create table keywords(
	picture_id int REFERENCES pictures(picture_id)  ,
	keyword varchar(16) NOT NULL
);
go


create table pictures_sets(
	pictures_sets_id int PRIMARY KEY,
	painter_id int REFERENCES painters(painter_id)  ,
	set_name varchar(16) NOT NULL,
	createTime datetime NOT NULL,
	remarks text,
	cover varchar(128) NOT NULL
);
go


create table pictures_sets_contents(
	pictures_sets_id int REFERENCES pictures_sets(pictures_sets_id)  ,
	picture_id int REFERENCES pictures(picture_id)  
);
go


create table comments(
	comment_id int identity(1,1) PRIMARY KEY,
	commentator_id int REFERENCES users(userID)  ,
	picture_id int REFERENCES pictures(picture_id)  ,
	content text NOT NULL,
	commentTime datetime default getdate()
);
go


create table comment_likes(
	comment_id int REFERENCES comments(comment_id)  ,
	liker_id int REFERENCES users(userID)  ,
);
go


create table favorites(
	userID int REFERENCES users(userID)  ,
	favorites_name varchar(16) NOT NULL,
	createTime datetime NOT NULL,
	favorites_id int identity(1,1) PRIMARY KEY
);
go


create table favorites_pictures(
	favorites_id int REFERENCES favorites(favorites_id)  ,
	picture_id int REFERENCES pictures(picture_id)  ,
	collectTime datetime NOT NULL
);
go

create table browses(
	browse_id int identity(1,1) PRIMARY KEY,
	picture_id int REFERENCES pictures(picture_id)  ,
	browser_id int REFERENCES users(userID)  ,
	browseTime datetime default getdate(),
);
go


create table supports(
	support_id int PRIMARY KEY,
	sponsor_id int REFERENCES users(userID)  ,
	receiver_id int REFERENCES painters(painter_id)  ,
	amount float check(amount > 0),
	sponsorTime datetime NOT NULL
);
go


create table private_letters(
	private_letter_id int PRIMARY KEY,
	sender_id int REFERENCES users(userID)  ,
	receiver_id int REFERENCES users(userID)  ,
	content text NOT NULL,
	sentTime dateTime NOT NULL
);
go

create table collections(
	user_id int REFERENCES users(userID),
	picture_id int REFERENCES pictures(picture_id)  ,
	collectTime dateTime default getdate()
);
go


--����ɾ��������
create trigger delete_comment on comments
instead of delete
as
begin
	--�α����
	declare @comment_id int;
	declare tempCursor CURSOR LOCAL FOR (select comment_id from deleted);
	open tempCursor;
	fetch next from tempCursor into @comment_id
	while @@FETCH_STATUS=0
	begin
		print('����ɾ��...');
		print(@comment_id);
		delete from comment_likes where comment_id = @comment_id;
		delete from comments where comment_id = @comment_id;
		fetch next from tempCursor into @comment_id;
	end

end
go
--����+Ƕ�� ɾ��������
create trigger delete_picture on pictures
instead of delete
as
begin
	declare @picture_id int;
	select @picture_id = picture_id from deleted;
	delete from keywords where picture_id = @picture_id;
	delete from comments where picture_id = @picture_id;
	delete from browses where picture_id = @picture_id;
	delete from collections where picture_id = @picture_id;
	delete from pictures where picture_id = @picture_id;
end


--drop trigger delete_comment
--drop trigger delete_picture

--���ڹؼ��ֵ���ɢ�ҷ������ԣ������ؼ�������
create clustered index keywords_index
on keywords(keyword)
--���ڱ������ģ����ѯ������£�������ʧЧ����ɱ�����ȥ�ȶԣ��ʲ���������

--����ͼƬ���ڻ�ʦid����������Ϊ�ܶ�����Ǹ��ݻ�ʦid���ͼƬ��
create nonclustered index picture_index
on pictures(painter_id)


exec sp_helpindex keywords
--select * from keywords with(index = keywords_index)
--drop index keywords.keywords_index
