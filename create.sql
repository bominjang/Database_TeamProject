CREATE DATABASE DB2021Team03;
use DB2021Team03;

##User
/*
ID: pk
nickname: 로그인 때 쓰일 유저 ID(index)
password: 로그인 때 쓰일 비밀번호
name: 유저 이름
birth: 생년월일
phone: 전화번호
join_time: 회원가입 날짜 및 시간
privacy_fg: 개인정보제공 동의 여부
admin_fg: 관리자인지 여부
delete_fg: 탈퇴한 사용자 여부
delete_time: 탈퇴한 날짜 및 시간
*/
CREATE TABLE DB2021_User(
ID int auto_increment not null,
nickname varchar(20) not null,
password varchar(20) not null,
name varchar(50) not null,
birth date,
phone varchar(11),
join_time timestamp not null,
privacy_fg char(1) not null,
admin_fg char(1) not null,
delete_fg char(1) not null,
delete_time timestamp,

primary key(ID)
);

CREATE INDEX Unick ON DB2021_User(nickname);


## Director
/*
ID: pk
name: 감독 이름(pk, index))
country: 출생국가
birth: 생일
*/
CREATE TABLE DB2021_Director(
ID int auto_increment not null,
name varchar(50) not null,
country varchar(20) not null,
birth date,

primary key(ID, name)
);

CREATE INDEX Dname ON DB2021_Director(name);


## Movie
/*
ID: pk
title: 영화 제목(index)
genre: 장르
country: 어느 나라 영화인지
running_time: 상영 시간
opening_date: 개봉일
director: 감독
plot: 줄거리
rating: 평점
age: 연령제한

Movie : Director = 1: Many 관계
*/
CREATE TABLE DB2021_MOVIE(
ID int auto_increment not null,
title varchar(20) not null,
genre varchar(20) not null,
country varchar(20) not null,
running_time int,
opening_date date,
director varchar(50) not null,
plot varchar(3000),
rating float not null,
age int not null,

primary key(ID),
foreign key(director) references DB2021_Director(name) on delete cascade on update cascade
);

CREATE INDEX Mtitle ON DB2021_MOVIE(title);



## Director_Prize
/*
ID: pk
prize: 상 이름
director: 상을 받은 감독(index)
movie: 어떤 영화로 상을 받았는지

Director: Prize = 1: Many 관계
*/
CREATE TABLE DB2021_Director_Prize(
ID int auto_increment not null,
prize varchar(100) not null,
director varchar(50) not null,
movie varchar(20) not null,

primary key(ID),
foreign key(director) references DB2021_Director(name) on delete cascade on update cascade,
foreign key(movie) references DB2021_MOVIE(title) on delete cascade on update cascade
);

CREATE INDEX DPidx ON DB2021_Director_Prize(director);



## Actor
/*
ID: pk
name: 배우 이름(pk, index)
country: 출생 국가
birth: 생년월일
*/
CREATE TABLE DB2021_Actor(
ID int auto_increment not null,
name varchar(50) not null,
country varchar(20) not null,
birth date,

primary key(ID, name)
);

CREATE INDEX Aname ON DB2021_Actor(name);


## Actor_Movie(Many to Many)
/*
ID: pk
name: 배우 이름(pk, index)
country: 출생 국가
birth: 생년월일

Actor : Movie = Many : Many 관계
*/
CREATE TABLE DB2021_Actor_Movie(
actor varchar(50) not null,
movie varchar(20) not null,

primary key(actor, movie),

foreign key(actor) references DB2021_Actor(name) on delete cascade on update cascade,
foreign key(movie) references DB2021_Movie(title) on delete cascade on update cascade
);

CREATE INDEX AMidx ON DB2021_Actor_Movie(actor);


## Actor_Prize
/*
ID: pk
prize: 배우가 받은 상
actor: 받은 배우(index)
movie: 어떤 영화에 출연해서 상을 받은건지

Actor: Prize = 1: Many 관계
*/
CREATE TABLE DB2021_Actor_Prize(
ID int auto_increment not null,
prize varchar(100) not null,
actor varchar(50) not null,
movie varchar(20) not null,

primary key(ID),
foreign key(actor) references DB2021_Actor(name) on delete cascade on update cascade,
foreign key(movie) references DB2021_Movie(title) on delete cascade on update cascade
);

CREATE INDEX APidx ON DB2021_Actor_Prize(actor);


## Review
/*
ID: pk
movie: 리뷰할 영화
nickname: 리뷰하는 유저의 닉네임(index)
create_time: 리뷰 작성 날짜 및 시간
rating: 유저가 매긴 평점
detail: 리뷰 내용

Review: User = Many: 1 관계
*/
CREATE TABLE DB2021_Review(
ID int auto_increment not null,
movie varchar(20) not null,
nickname varchar(20) not null,
create_time timestamp not null,
rating float not null,
detail varchar(500) not null,

primary key(ID),
foreign key(movie) references DB2021_Movie(title) on delete cascade on update cascade,
foreign key(nickname) references DB2021_User(nickname) on delete cascade on update cascade
);

CREATE INDEX Ridx ON DB2021_Review(nickname);


# view들
CREATE VIEW DB2021_titleExist AS SELECT title FROM DB2021_Movie;
CREATE VIEW DB2021_DnameExist AS SELECT name FROM DB2021_Director;
CREATE VIEW DB2021_AnameExist AS SELECT name FROM DB2021_Actor;


# director data insert
insert into DB2021_Director(name, country, birth) VALUES
('조진모','대한민국','1974-01-28'),
('닉 카사베츠','미국','1959-05-21'),
('김주환','대한민국','1981-01-01'),
('데미안 셔젤','미국','1985-01-19'),
('정다원','대한민국','1985-10-19'),
('조의석','대한민국','1976-01-01'),
('리 아이작 정','미국','1978-10-19'),
('크리스토퍼 놀란','미국','1970-07-30'),
('웨스 앤더슨','미국','1969-05-01'),
('마틴 스콜세지','미국','1942-11-17'),
('루벤 플레셔','미국','1974-10-31'),
('봉준호','한국','1969-09-14'),
('장준환','한국','1970-01-18'),
('나홍진','한국','1974-01-01'),
('이해영','한국','1973-10-18'),
('최동훈','한국','1971-01-01'),
('마이크 뉴웰', '영국', '1948-03-28'),
('김용화', '한국', '1971-09-25'),
('히로키 류이치', '일본', '1954-01-01'),
('라세 할스트롬', '스웨덴', '1946-06-02'),
('캐서린 하드윅', '미국', '1955-10-22'),
('기예르모 델 토로', '멕시코', '1964-10-09'),
('팀 버튼', '미국', '1958-08-25');


# movie data insert
insert into DB2021_MOVIE(title, genre, country, running_time, opening_date, director, plot, rating, age) VALUES
('비와 당신의 이야기', 
'로맨스',
'한국',
117,
'2021-04-28',
'조진모',
'\"이건 기다림에 관한 이야기다\" 뚜렷한 꿈도 목표도 없이 지루한 삼수 생활을 이어가던 영호(강하늘),
오랫동안 간직해온 기억 속 친구를 떠올리고 무작정 편지를 보낸다.
우연히 시작된 편지는 무채색이던 두 사람의 일상을 설렘과 기다림으로 물들이기 시작하고, 영호는 12월 31일 비가 오면 만나자는 
가능성이 낮은 제안을 하게 되는데...',
0,
0),
('메이킹 패밀리',
'로맨스',
'한국',
100,
'2016-11-24',
'조진모', 
'“엄마! 아빠 찾아올게!”
아들에게는 최고의 엄마, 방송국에서는 실력있는 PD로 인정받으며 완벽한 싱글맘으로 살고 있는 ‘미연’(김하늘)에게 날벼락 같은 일이 벌어졌다. 아들 태봉(문메이슨)이 ‘생물학적 아빠’를 찾겠다며 쪽지를 남기고 사라진 것. ‘아니, 어떻게 애 혼자 중국에 가?’
유명 재력가의 아들이지만, 가업을 잇기보다는 화려한 싱글 아티스트로서의 삶을 만끽하는 ‘주리옌’(이치정)에게 갑자기 한 꼬마가 나타나서 우기기 시작한다. ‘얘가 내 아들이라고?’
화려한 싱글라이프를 즐기는 그들에게 별안간 문제가 생겼다. \“저의 생부는 누구입니까?\”',
0,
12),
('노트북',
'로맨스',
'미국',
123,
'2004-11-26',
'닉 카사베츠',
'17살, ‘노아’는 밝고 순수한 ‘앨리’를 보고 첫눈에 반한다.빠른 속도로 서로에게 빠져드는 둘.그러나 이들 앞에 놓인 장벽에 막혀 이별하게 된다.
24살, ‘앨리’는 우연히 신문에서 ‘노아’의 소식을 접하고
잊을 수 없는 첫사랑 앞에서
다시 한 번 선택의 기로에 서게 되는데…열일곱의 설렘, 스물넷의 아픈 기억, 그리고 마지막까지…한 사람을 지극히 사랑했으니 내 인생은 성공한 인생입니다.',
0,
15),
('청년경찰',
'액션',
'한국',
109,
'2017-08-09',
'김주환',
'현장경험 전무, 수사는 책으로 배웠다!
“그냥 우리가 잡아볼게요”
의욕충만 경찰대생 기준(박서준) X 이론백단 경찰대생 희열(강하늘)
 둘도 없는 친구인 두 사람은 외출을 나왔다 우연히 납치 사건을 목격하게 된다.
 목격자는 오직 두 사람 뿐! 기준과 희열은 학교에서 배운 대로 지체 없이 경찰에 신고한다. 하지만 복잡한 절차와 부족한 증거로 수사는 전혀 진행될 기미가 보이지 않는 상황! 1분 1초가 급박한 상황에서 아까운 시간만 흘러가자,
 기준과 희열은 직접 발로 뛰는 수사에 나서기로 하고 예측 불가능한 상황을 마주하게 되는데…
 전공지식 총동원! 파릇파릇한 놈들의 혈기왕성 실전수사가 시작된다!',
 0,
 15),
('라라랜드',
'로맨스',
'미국',
128,
'2016-12-07',
'데미안 셔젤',
'황홀한 사랑, 순수한 희망, 격렬한 열정…
이 곳에서 모든 감정이 폭발한다!
꿈을 꾸는 사람들을 위한 별들의 도시 ‘라라랜드’. 재즈 피아니스트 ‘세바스찬’(라이언 고슬링)과 
배우 지망생 ‘미아’(엠마 스톤), 인생에서 가장 빛나는 순간 만난 두 사람은 미완성인 서로의 무대를 만들어가기 시작한다.',
0,
12),
('걸캅스',
'액션',
'한국',
107,
'2019-05-09',
'정다원',
'민원실 퇴출 0순위 전직 전설의 형사 미영과 민원실로 밀려난 현직 꼴통 형사 지혜
 집에서는 눈만 마주쳐도 으르렁 대는 시누이 올케 사이인 두 사람은
 민원실에 신고접수를 하기 위해 왔다가 차도에 뛰어든 한 여성을 목격하고
 그녀가 48시간 후 업로드가 예고된 디지털 성범죄 사건의 피해자란 사실을 알게 된다.
 강력반, 사이버 범죄 수사대, 여성청소년계까지 경찰 내 모든 부서들에서
 복잡한 절차와 인력 부족을 이유로 사건이 밀려나자
 미영과 지혜는 비공식 수사에 나서기로 결심한다.
 수사가 진전될수록 형사의 본능이 꿈틀대는 미영과 정의감에 활활 불타는 지혜는
 드디어 용의자들과 마주할 기회를 잡게 되는데…
 걸크러시 콤비의 비공식 합동 수사가 펼쳐진다!',
 0,
 15),
('사자',
'액션/미스터리/공포',
'한국',
129,
'2019-07-31',
'김주환',
'어릴 적 아버지를 잃은 뒤 세상에 대한 불신만 남은 격투기 챔피언 \‘용후\’(박서준).
어느 날 원인을 알 수 없는 깊은 상처가 손바닥에 생긴 것을 발견하고,
도움을 줄 누군가가 있다는 장소로 향한다.
그곳에서 악의 편에 설 것인가 악에 맞설 것인가 2019년 여름, 신의 사자가 온다!',
0,
15),
('마스터',
'액션/범죄',
'한국',
143,
'2016-12-21',
'조의석',
'\"썩은 머리 이번에 싹 다 잘라낸다\"
화려한 언변, 사람을 현혹하는 재능, 정관계를 넘나드는 인맥으로 수만 명 회원들에게 사기를 치며 승승장구해 온 원네트워크 ‘진회장’(이병헌). 반년간 그를 추적해 온 지능범죄수사팀장 ‘김재명’(강동원)은 진회장의 최측근인 ‘박장군’(김우빈)을 압박한다. 원네트워크 전산실 위치와 진회장의 로비 장부를 넘기라는 것. 뛰어난 프로그래밍 실력과 명석한 두뇌로 원네트워크를 키워 온 브레인 박장군은 계획에 차질이 생긴 것을 감지하자 빠르게 머리를 굴리기 시작한다.
진회장은 물론 그의 뒤에 숨은 권력까지 모조리 잡기 위해 포위망을 좁혀가는 재명, 오히려 이 기회를 틈타 돈도 챙기고 경찰의 압박에서도 벗어날 계획을 세우는 장군. 하지만 진회장은 간부 중에 배신자가 있음을 눈치채고, 새로운 플랜을 가동하는데…
지능범죄수사대, 희대의 사기범, 그리고 브레인
서로 속고 속이는 추격이 시작된다!',
0,
15),
('미나리',
'드라마',
'한국',
115,
'2021-03-03',
'리 아이작 정',
'2021년 전 세계가 기다린 어느 한국 가족의 원더풀한 이야기
\"미나리는 어디서든 잘 자라\"
낯선 미국, 아칸소로 떠나온 한국 가족.
가족들에게 뭔가 해내는 걸 보여주고 싶은 아빠 제이콥(스티븐 연)은
자신만의 농장을 가꾸기 시작하고 엄마 모니카(한예리)도 다시 일자리를 찾는다.
아직 어린 아이들을 위해 ‘모니카’의 엄마 ‘순자’(윤여정)가 함께 살기로 하고
가방 가득 고춧가루, 멸치, 한약 그리고 미나리씨를 담은 할머니가 도착한다.
의젓한 큰딸 앤(노엘 케이트 조)과 장난꾸러기 막내아들 데이빗(앨런 김)은
여느 그랜마같지 않은 할머니가 영- 못마땅한데…
함께 있다면, 새로 시작할 수 있다는 희망으로
하루하루 뿌리 내리며 살아가는
어느 가족의 아주 특별한 여정이 시작된다!',
0,
12),
('테넷',
'액션/SF',
'미국',
150,
'2020-08-26',
'크리스토퍼 놀란',
'당신에게 줄 건 한 단어 `테넷` 이해하지 말고 느껴라! 시간의 흐름을 뒤집는 인버전을 통해 현재와 미래를 오가며 세상을 파괴하려는 사토르(케네스 브래너)를 막기 위해 투입된 작전의 주도자(존 데이비드 워싱턴). 
인버전에 대한 정보를 가진 닐(로버트 패틴슨)과 미술품 감정사이자 사토르에 대한 복수심이 가득한 그의 아내 캣(엘리자베스 데비키)과 
협력해 미래의 공격에 맞서 제3차 세계대전을 막아야 한다!',
0,
12),
('곡성',
'미스터리',
'한국',
156,
'2016-05-12',
'나홍진',
'낯선 외지인(쿠니무라 준)이 나타난 후 벌어지는 의문의 연쇄 사건들로 마을이 발칵 뒤집힌다. 경찰은 집단 야생 버섯 중독으로 잠정적 결론을 내리지만 모든 사건의 원인이 그 외지인 때문이라는 소문과 의심이 걷잡을 수 없이 퍼져 나간다.
경찰 \‘종구\’(곽도원)는 현장을 목격했다는 여인 \‘무명\’(천우희)을 만나면서 외지인에 대한 소문을 확신하기 시작한다. 딸 \‘효진\’(김환희)이 피해자들과 비슷한 증상으로 아파오기 시작하자 다급해진 \‘종구\’. 외지인을 찾아 난동을 부리고, 무속인 \‘일광\’(황정민)을 불러들이는데....',
0,
15
),
('그랜드 부다페스트 호텔',
'미스터리',
'독일, 영국',
100,
'2014-03-20',
'웨스 앤더슨',
'세계 최고 부호 마담 D.의 피살사건! 전 세계를 매료시킨 황홀한 이야기가 다시 시작된다! 1927년 세계대전이 한창이던 어느 날, 세계 최고의 부호 마담 D.가 의문의 살인을 당한다.
유력한 용의자로 지목된 사람은 바로 전설적인 호텔 지배인이자 그녀의 연인 \‘구스타브\’!구스타브는 누명을 벗기 위해 충실한 로비보이 ‘제로’에게 도움을 청하고, 그 사이 구스타브에게 남겨진 마담 D.의 유산을 노리던 그녀의 아들 \‘드미트리\’는 무자비한 킬러를 고용해 [그랜드 부다페스트 호텔]을 찾게 되는데…',
0,
15),
('셔터 아일랜드',
'미스터리',
'미국',
138,
'2010-03-18',
'마틴 스콜세지', 
'누군가 사라졌다
보스턴 셔터아일랜드의 정신병원에서 환자가 실종되는 사건이 발생한다. 연방보안관 테디 다니엘스(레오나르도 디카프리오)는 수사를 위해 동료 척(마크 러팔로)과 함께 셔터아일랜드로 향한다. 셔터 아일랜드에 위치한 이 병원은 중범죄를 저지른 정신병자를 격리하는 병동으로 탈출 자체가 불가능하다.
하지만 자식 셋을 죽인 혐의를 받고 있는 여인이 이상한 쪽지만을 남긴 채 감쪽같이 사라지고, 테디는 수사를 위해 의사, 간호사, 병원관계자 등을 심문하지만 모두 입이라도 맞춘 듯 꾸며낸 듯한 말들만 하고, 수사는 전혀 진척되지 않는다. 설상가상 폭풍이 불어 닥쳐 테디와 척은 섬에 고립되게 되고, 그들에게 점점 괴이한 일들이 일어나기 시작한다.',
0,
15),
('인셉션',
'스릴러',
'미국',
147,
'2010-07-21',
'크리스토퍼 놀란', 
'타인의 꿈에 들어가 생각을 훔치는 특수 보안요원 코브.
그를 이용해 라이벌 기업의 정보를 빼내고자 하는 사이토는 코브에게 생각을 훔치는 것이 아닌, 생각을 심는 \‘인셉션\’ 작전을 제안한다. 성공 조건으로 국제적인 수배자가 되어있는 코브의 신분을 바꿔주겠다는 거부할 수 없는 제안을 하고, 사랑하는 아이들에게 돌아가기 위해 그 제안을 받아들인다. 최강의 팀을 구성, 표적인 피셔에게 접근해서 \‘인셉션\’ 작전을 실행하지만 예기치 못한 사건들과 마주하게 되는데…
꿈 VS 현실
시간, 규칙, 타이밍 모든 것이 완벽해야만 하는,
단 한 번도 성공한 적 없는 ‘인셉션’ 작전이 시작된다!',
0,
12),
('베놈',
'스릴러',
'미국',
107,
'2018-10-03',
'루벤 플레셔', 
'영웅인가, 악당인가 진실을 위해서라면 몸을 사리지 않는 정의로운 열혈 기자 에디 브록 거대 기업 라이프 파운데이션의 뒤를 쫓던 그는 이들의 사무실에 잠입했다가 실험실에서 외계 생물체 심비오트의 기습 공격을 받게 된다.
심비오트와 공생하게 된 에디 브록은 마침내 한층 강력한 베놈으로 거듭나고, 악한 존재만을 상대하려는 에디 브록의 의지와 달리 베놈은 난폭한 힘을 주체하지 못하는데…!지배할 것인가, 지배당할 것인가',
0,
15),
('기생충',
'드라마',
'한국',
131,
'2019-05-30',
'봉준호', 
'\“폐 끼치고 싶진 않았어요\” 전원백수로 살 길 막막하지만 사이는 좋은 기택(송강호) 가족.
장남 기우(최우식)에게 명문대생 친구가 연결시켜 준 고액 과외 자리는 모처럼 싹튼 고정수입의 희망이다. 온 가족의 도움과 기대 속에 박사장(이선균) 집으로 향하는 기우. 글로벌 IT기업 CEO인 박사장의 저택에 도착하자 젊고 아름다운 사모님 연교(조여정)가 기우를 맞이한다.
그러나 이렇게 시작된 두 가족의 만남 뒤로, 걷잡을 수 없는 사건이 기다리고 있었으니…',
0,
15),
('추격자',
'범죄',
'한국',
123,
'2008-02-14',
'나홍진',
'그날밤 놈을 쫓던 단 한 명의(추격자)놈을 잡은 건 경찰도 검찰도 아니었다 대한민국을 뒤흔든 희대의 살인마 출장안마소(보도방)를 운영하는 전직 형사 중호, 최근 데리고 있던 여자들이 잇달아 사라지는 일이 발생하고, 미진 마저도 연락이 두절된다..',
0,
20),
('화이:괴물을 삼킨 아이',
'스릴러',
'한국',
126,
'2013-10-09',
'장준환', 
'5명의 범죄자를 아버지로 둔 소년 \‘화이\’.
냉혹한 카리스마의 리더 \‘석태\’, 운전전문 말더듬이 \‘기태\’, 이성적 설계자 \‘진성\’, 총기전문 저격수 \‘범수\’, 냉혈한 행동파 \‘동범\’까지.
화이는 학교 대신 5명의 아버지들이 지닌 기술을 배우며 남들과 다르게 자라왔지만, 자신의 과거를 모른 채 순응하며 지내왔다.
하지만 화이가 아버지들만큼 강해지기를 바라는 리더 석태는 어느 날 범죄 현장으로 화이를 이끌고...
한 발의 총성이 울러 퍼진 그 날 이후.
숨겨진 진실을 마주하게 된 화이와 그를 둘러싼 모든 것들이 변화하기 시작한다!
\“아버지... 왜 절 키우신 거에요?\”',
0,
20),
(
'독전',
'범죄',
'한국',
123,
'2018-05-22',
'이해영', 
'아시아 최대 마약 조직, 실체 없는 적을 추적하라!
의문의 폭발 사고 후, 오랫동안 마약 조직을 추적해온 형사 ‘원호’(조진웅)의 앞에 조직의 후견인 ‘오연옥’(김성령)과 버림받은 조직원 ‘락’(류준열)이 나타난다. 그들의 도움으로 아시아 마약 시장의 거물 ‘진하림’(김주혁)과 조직의 숨겨진 인물 ‘브라이언’(차승원)을 만나게 되면서 그 실체에 대한 결정적 단서를 잡게 되는데…
끝까지 의심하라!
독한 자들의 전쟁이 시작된다!',
0,
15),
(
'타짜',
'범죄',
'한국',
139,
'2006-09-28',
'최동훈', 
'가구공장에서 일하며 남루한 삶을 사는 고니는 대학보다 가난을 벗어나게 해줄 돈이 우선인 열혈 천방지축 청년! 어느 날 고니는, 가구공장 한 켠에서 박무석 일행이 벌이는 화투판에 끼게 된다. 스무장의 화투로 벌이는 \"섯다\" 한 판! 하지만 고니는 그 판에서 삼년 동안 모아두었던 돈 전부를 날리고 만다. 그것이 전문도박꾼 타짜들이 짜고 친 판이었단 사실을 뒤늦게 안 고니는 박무석 일행을 찾아 나서고, 도박으로 시비가 붙은 한 창고에서 우연인 듯 필연처럼 전설의 타짜 평경장을 만난다. 그리고 잃었던 돈의 다섯 배를 따면 화투를 그만두겠단 약속을 하고, 그와 함께 본격적인 꽃싸움에 몸을 던지기 위한 동행길에 오른다.',
0,
20),
('해리포터와 불의 잔', '판타지', '영국', 156, '2021-04-10', '마이크 뉴웰',
'해리 포터 일생일대 최대 난관!
 요즘 들어 매일 꾸는 악몽 때문에 이마의 상처에 더욱 통증을 느끼는 해리(다니엘 래드클래프)는 친구 론(루퍼트 그린트)과 헤르미온느(엠마 왓슨)와 함께 퀴디치 월드컵에 참가해 악몽에서 벗어날 수 있게 돼 마냥 기쁘다. 그러나 퀴디치 캠프장 근방 하늘에 불길한 기운, 바로 마왕 볼드모트의 상징인 어둠의 표식이 나타난다. 볼드모트가 13년 전에 자취를 감춘 뒤 감히 모습을 드러내지 못했던 그의 추종자 데스 이터들이 그 표식을 불러낸 것이다. 두려움으로 가득 찬 해리는 안전한 호그와트 마법학교로 돌아가고 싶어한다. 덤블도어 교장(마이클 갬본 경)이라면 자신을 지켜줄 수 있을 것이기에….',
 0,12),
 ('신과 함께-인과 연', '판타지', '한국', 141, '2021-04-03', '김용화', 
'천 년 동안 48명의 망자를 환생시킨 저승 삼차사, 한 명만 더 환생시키면 그들도 새로운 삶을 얻을 수 있다.
 하지만 강림(하정우)은 원귀였던 수홍(김동욱)을 자신들의 마지막 귀인으로 정하는 이해할 수 없는 선택을 한다.
 저승법 상 원귀는 소멸되어야 마땅하나 염라대왕(이정재)은 저승 삼차사에게 새로운 조건을 내걸며 강림의 제안을 수락한다.
 염라의 조건은 성주신(마동석)이 버티고 있어 저승 차사들이 가는 족족 실패하는 허춘삼 노인을 수홍의 재판이 끝나기 전까지 저승으로 데려오는 것..',
 0, 12),
 ('나미야 잡화점의 기적',
 '판타지',
 '일본',
 130,
 '2021-05-06',
 '히로키 류이치',
 '우연히 나미야 잡화점에 숨어 든 3인조 도둑
 아츠야, 쇼타, 고헤이는 잡화점 문 틈으로
 생선가게 뮤지션이라고 이름이 적힌 편지 한 통을 받게 된다.
 이들은 호기심에 열어본 편지가 32년 전에 쓰여진 사실을 알게 되고,
 자신들이 장난 삼아 보낸 답장이 과거와 현재에 영향을 준다는 것을 깨닫는다.
 그러는 사이 또다시 편지가 도착하고,
 이곳에서 벌어진 일들이 모두 우연이 아닌
 하나의 인연으로 연결된 것임을 알게 되는데...',
 0,
 0),
('호두까기 인형과 4개의 왕국',
'판타지', '미국', 99, '2021-04-16', '라세 할스트롬',
'크리스마스 이브,
 대부 ‘드로셀마이어’의 파티에 참석한 \‘클라라\’는
 돌아가신 엄마의 마지막 크리스마스 선물을 열어줄 황금 열쇠를 찾아 나선다.
 대부에게 건네받은 황금실을 따라 마법의 세상 속으로 들어간 ‘클라라’는 호두까기 병정과 함께
 3개의 왕국을 지나면서 다양한 사건과 사람들을 만나 환상적인 모험을 즐긴다.
 그러나 엄마의 수수께끼를 풀 수 있는 열쇠를 얻기 위해서는
 모두가 두려워하는 네 번째 왕국으로 가야만 하는데…
 황금 열쇠를 따라가면,
 지금껏 본 적 없는 마법의 세계가 열린다!',
 0,
 0),
('트와일라잇',
'판타지, 로맨스, 스릴러', '미국', 121, '2021-04-26', '캐서린 하드윅', 
'17세의 평범한 고등학생 소녀 \‘벨라\’는 집안 사정으로
 워싱턴 주 포크스에 있는 아빠의 집으로 이사를 온다.
 전학 첫날, \‘벨라\’는 냉담하지만 자신을 무장 해제시킬 정도로
 잘생긴 \‘에드워드\’와 마주치고, 전율과 두려움 넘치는 인생의 전환을 맞이한다.
 ‘에드워드’와 돌이킬 수 없는 사랑에 빠져든 \‘벨라\’.
 하지만 \‘에드워드\’와 그의 가족이 뱀파이어 일족이라는
 사실을 알게 되고, 예기치 못한 운명에 빠져든다.',
 0,
 12),
 ('레드 라이딩 후드',
 '판타지, 로맨스', '미국, 캐나다', 100, '2021-04-18', '캐서린 하드윅',
 '빨간모자야, 사랑에 빠지지마… 옛날 어느 외딴 마을에 빨간모자를 쓴 발레리라는 아름다운 소녀가 살고 있었어요. 마을의 외톨이 피터와 사랑에 빠진 발레리는 부잣집 아들 헨리와 결혼하라는 부모님을 피해 마을을 떠나기로 결심했지요. 
 하지만 붉은 달이 뜬 그날 밤, 어둠의 숲에 사는 늑대에게 언니가 죽임을 당하고 말았어요. 분노한 마을 사람들은 솔로몬 신부에게 도움을 청했지만, 신부는 마을 사람들 속에 늑대가 인간의 모습을 하고 숨어 있다고 말했어요. 달이 뜰 때마다
 하나, 둘, 죽어가는 사람들이 늘어가고, 우연히 발레리는 자신과 관계된 누군가가 늑대 인간이 아닐까 생각했어요. 그리고 모든 비밀을 풀기 위해 스스로 제물이 되기로 결심하고, 달이 뜨는 밤 홀로 산으로 향하게 되었답니다. 
 그리고 발레리 앞에 나타난 늑대인간은 바로!',
 0,
 15),
 ('판의 미로-오델리아와 세 개의 열쇠',
 '판타지, 드라마', '미국, 멕시코, 스페인', 119, '2021-04-28', '기예르모 델 토로',
'1944년 스페인, 내전은 끝났지만 숲으로 숨은 시민군은 파시스트 정권에 계속해서 저항했고 그들을 진압하기 위해 정부군이 곳곳에 배치된다.
 \‘오필리아\’는 만삭의 엄마 \‘카르멘\’과 함께 새아버지 \‘비달\’ 대위가 있는 숲속 기지로 거처를 옮긴다.
 정부군 소속으로 냉정하고 무서운 비달 대위를 비롯해 모든 것이 낯설어 두려움을 느끼던 오필리아는 어느 날 숲속에서 숨겨진 미로를 발견한다.
 그리고 그곳에서 자신을 “산이고 숲이자 땅”이라 소개하는 기괴한 모습의 요정 \‘판\’과 만난다.
 오필리아를 반갑게 맞이한 판은, 그녀가 지하 왕국의 공주 \‘모안나\’이며 보름달이 뜨기 전까지 세 가지 임무를 끝내면 돌아갈 수 있다고 알려주면서 미래를 볼 수 있는 “선택의 책”을 건넨다.
 오필리아는 전쟁보다 더 무서운 현실 속에서 인간 세계를 떠나 지하 왕국으로 돌아가기로 결심하게 되는데…
 용기, 인내, 그리고 마지막 임무…
 판의 미로가 다시 열리고, 환상과 현실의 경계가 무너진다!',
 0,
 15),
 ('셰이프 오브 워터: 사랑의 모양',
 '판타지, 로맨스, 스릴러, 드라마', '미국', 123,'2021-05-22', '기예르모 델 토로',
'우주 개발 경쟁이 한창인 1960년대,
 미 항공우주 연구센터의 비밀 실험실에서 일하는 언어장애를 지닌 청소부 엘라이자(샐리 호킨스)의 곁에는
 수다스럽지만 믿음직한 동료 젤다(옥타비아 스펜서)와
 서로를 보살펴주는 가난한 이웃집 화가 자일스(리차드 젠킨스)가 있다.
 어느 날 실험실에 온몸이 비늘로 덮인 괴생명체가 수조에 갇힌 채 들어오고,
 엘라이자는 신비로운 그에게 이끌려 조금씩 다가가게 된다.
 음악을 함께 들으며 서로 교감하는 모습을 목격한 호프스테틀러 박사(마이클 스털버그)는
 그 생명체에게 지능 및 공감 능력이 있다는 사실을 알게 되고,
 실험실의 보안책임자인 스트릭랜드(마이클 섀넌)는 그를 해부하여 우주 개발에 이용하려 한다.
 이에 엘라이자는 그를 탈출시키기 위한 계획을 세우게 되는데…',
 0,
 20),
 ('미스 페레그린과 이상한 아이들의 집',
 '판타지', '미국', 127, '2021-05-28', '팀 버튼', 
'할아버지의 죽음의 단서를 쫒던 \‘제이크\’ 는 시간의 문을 통과해 놀라운 비밀과 마주한다.
 시간을 조정하는 능력을 가진 \‘미스 페레그린\’ 과 그녀의 보호아래 무한 반복되는 하루를 사는 \‘특별한 능력의 아이들\’,
 그리고 그들을 사냥하는 보이지 않는 무서운 적 ‘할로게스트’
 미스 페레그린과 제이크를 비롯한 아이들은 살아남기 위해 \‘할로게스트\’ 에 맞서야 한다.
 시간과 공간을 넘나들며 펼쳐지는 이들의 대결이 팀버튼의 마법같은 상상력으로 펼쳐진다.',
 0,
 12),
 ('유령신부',
 '판타지', '미국', 77, '2021-05-05', '팀 버튼', 
'지루한 일상에 비해 활기가 넘치는 지하세계, 인간들에 비해 더욱 생동감 넘치는 유령들의 축제 등 감독 특유의 기이하고 몽환적인 상상의 세계가 놀랄 만큼 흥미롭게 펼쳐진다. 여기에 팀 버튼의 영원한 단짝 조니 뎁을 비롯해 헬레나 본햄 카터, 에밀리 왓슨, 알버트 피니, 크리스토퍼 리 등 연기파 배우들의 목소리 출연이 작품에 생기를 불어 넣는다. 특히, 배우들과 꼭 닮은 모습의 캐릭터들은 <유령신부>가 즐거운 또 하나의 이유이다.
 결혼이 두려운 소심한 신랑 빅터(조니 뎁)가 겪는 환상적인 이야기. 결혼식을 하루 앞둔 빅터는 예행연습에서 계속 실수를 하자 밖으로 뛰쳐나간다. 숲 속에서 홀로 연습하던 도중, 땅 위로 튀어나온 손가락 뼈에 반지를 끼웠다가 유령신부(헬레나 본햄 카터)의 오해로 지하세계에 끌려가게 되는 색다른 경험을 하게 되는 것이다.',
0,
0);


# director_prize data insert
insert into DB2021_Director_Prize(director, prize, movie)
values
('닉 카사베츠','1997 제50회 칸 영화제','노트북'),
('김주환','미국 작가 조합상 TV 코미디 시리즈 각본상','청년경찰'),
('데미안 셔젤','2018 제22회 할리우드 필름어워즈','라라랜드'),
('조의석','제13회 뉴욕 아시아 영화제 떠오르는 아시아스타상','마스터'),
('리 아이작 정','제26회 크리틱스 초이스 시상식 외 9건','미나리'),
('크리스토퍼 놀란','제62회 다비드 디 도나텔로 어워드 외 20건','테넷');

insert into DB2021_Director_Prize(director, movie, prize)
values ('나홍진','곡성','제 11회 아시안 필름 어워드 (최우수감독상)'),
('웨스 앤더슨','그랜드 부다페스트 호텔','제 68회 영국 아카데미 시상식 (각본상)'),
('봉준호','기생충','제 72회 미국 작가 조합상 (각본상)'),
('나홍진','추격자','제 44회 백상예술대상 (영화 신인감독상)'),
('장준환','화이:괴물을 삼킨 아이','제 3회 마리끌레르 영화제 (파이오니어 감독상)'),
('최동훈','타짜','제 43회 백상예술대상 (영화 대상)'),
('마이크 뉴웰', '해리포터와 불의 잔', '1995년 48회 영국 아카데미 시상식 데이빗 린 상'),
('김용화', '신과 함께-인과 연', '2018년 54회 백상예술대학 영화 감독상 수상'),
('히로키 류이치', '나미야 잡화점의 기적', '2006년 19회 싱가포르 국제 영화제 작품성-아시아 장편 수상'),
('라세 할스트롬', '호두까기 인형과 4개의 왕국', '2007년 17회 스톡홀름 영화제 평생공로상'),
('캐서린 하드윅', '트와일라잇', '2009년 18회 MTV 영화&TV 어워즈 최고의 영화상 수상'),
('캐서린 하드윅', '레드 라이딩 후드', '2009년 18회 MTV 영화&TV 어워즈 최고의 영화상 수상'),
('기예르모 델 토로', '판의 미로-오델리아와 세 개의 열쇠', '2018년 23회 크리틱스 초이스 시상식 작품상 수상'),
('기예르모 델 토로', '셰이프 오브 워터: 사랑의 모양','2018년 23회 크리틱스 초이스 시상식 작품상 수상'),
('팀 버튼', '미스 페레그린과 이상한 아이들의 집', '2014년 27회 도쿄국제 영화제 사무라이상 수상'),
('팀 버튼', '유령신부', '2014년 27회 도쿄국제 영화제 사무라이상 수상');


# actor data insert
insert into db2021_actor(name, country, birth)
values 
('강하늘','대한민국','1989-02-21'),
('천우희','대한민국','1987-01-20'),
('라이언 고슬링','캐나다','1980-11-12'),
('레이첼 맥아담스','캐나다','1978-11-17'),
('박서준','대한민국','1988-12-16'),
('라미란','대한민국','1975-03-06'),
('이성경','대한민국','1990-08-10'),
('안성기','대한민국','1952-01-01'),
('우도환','대한민국','1992-07-12'),
('이병헌','대한민국','1970-07-12'),
('강동원','대한민국','1981-01-18'),
('윤여정','대한민국','1947-06-19'),
('한예리','대한민국','1984-12-23'),
('스티븐 연','대한민국','1983-12-21'),
('존 데이비드 워싱턴','미국','1984-07-28'),
('곽도원','한국','1973-05-17'),
('황정민','한국','1970-09-01'),
('레오나르도 디카프리오','미국','1974-11-11'),
('톰 하디','영국','1977-09-15'),
('조여정','한국','1981-02-10'),
('김윤석','한국','1968-01-21'),
('박소담','한국','1991-09-08'),
('김윤석','한국','1968-01-21'),
('여진구','한국','1997-08-13'),
('조진웅','한국','1976-04-02'),
('김혜수','한국','1970-09-05'),
('김윤석','한국','1968-01-21'),
('조승우','한국','1980-03-28'),
('다니엘 래드클리프', '영국', '1989-07-23'),
('엠마 왓슨', '프랑스', '1990-04-15'),
('루퍼트 그린트', '영국', '1988-08-24'),
('하정우', '한국', '1978-03-11'),
('주지훈', '한국', '1982-05-16'),
('김향기', '한국', '2000-08-09'),
('야마다 료스케', '일본', '1993-05-09'),
('키이라 니이틀리', '영국', '1965-03-26'),
('매켄지 포이', '미국', '2000-11-10'),
('크리스틴 스튜어트', '미국', '1990-04-09'),
('로버트 패틴슨', '영국', '1986-05-13'),
('테오 제임스', '영국', '1984-12-16'),
('이바나 바쿠에로', '스페인', '1994-06-11'),
('샐리 호킨스', '영국', '1976-04-27'),
('마이클 섀넌', '미국', '1974-08-07'),
('에바 그린', '프랑스', '1980-07-06'),
('조니 뎁', '미국', '1963-06-09');


# actor_movie data insert
insert into DB2021_Actor_Movie(actor, movie)
values 
('강하늘','비와 당신의 이야기'),
('천우희','비와 당신의 이야기'),
('라이언 고슬링','노트북'),
('레이첼 맥아담스','노트북'),
('박서준','청년경찰'),
('라미란','걸캅스'),
('이성경','걸캅스'),
('안성기','사자'),
('우도환','사자'),
('이병헌','마스터'),
('강동원','마스터'),
('윤여정','미나리'),
('한예리','미나리'),
('스티븐 연','미나리'),
('존 데이비드 워싱턴','테넷'),
('곽도원','곡성'),
('황정민','곡성'),
('레오나르도 디카프리오','셔터 아일랜드'),
('레오나르도 디카프리오','인셉션'),
('톰 하디','인셉션'),
('톰 하디','베놈'),
('조여정','기생충'),
('김윤석','추격자'),
('박소담','기생충'),
('김윤석','화이:괴물을 삼킨 아이'),
('여진구','화이:괴물을 삼킨 아이'),
('조진웅','독전'),
('김혜수','타짜'),
('김윤석','타짜'),
('조승우','타짜'),
('다니엘 래드클리프','해리포터와 불의 잔'),
('엠마 왓슨','해리포터와 불의 잔'),
('루퍼트 그린트', '해리포터와 불의 잔'),
('하정우', '신과 함께-인과 연'),
('주지훈', '신과 함께-인과 연'),
('김향기', '신과 함께-인과 연'),
('야마다 료스케','나미야 잡화점의 기적'),
('키이라 니이틀리', '호두까기 인형과 4개의 왕국'),
('매켄지 포이', '호두까기 인형과 4개의 왕국'),
('크리스틴 스튜어트', '트와일라잇'),
('로버트 패틴슨','트와일라잇'),
('이바나 바쿠에로','판의 미로-오델리아와 세 개의 열쇠'),
('샐리 호킨스','셰이프 오브 워터: 사랑의 모양'),
('마이클 섀넌', '셰이프 오브 워터: 사랑의 모양'),
('에바 그린','미스 페레그린과 이상한 아이들의 집'),
('조니 뎁','유령신부');


# actor_prize data insert
insert into DB2021_Actor_Prize(actor, prize, movie)
values 
('라이언 고슬링','2017.02 제32회 산타바바라국제영화제','노트북'),
('레이첼 맥아담스','틴 초이스 어워드 코미디부문상 외 4건','노트북'),
('박서준','2017 대종상 신인남우상','청년경찰'),
('이성경','24회 춘사영화상(특별인기상)','걸캅스'),
('우도환','40회 황금촬영상 시상식(신인남우상)','사자'),
('이병헌','제10회 아시안필름어워즈(AFA) 남우주연상','마스터'),
('윤여정','아카데미 여우조연상 외 다수','미나리'),
('스티븐 연','2021 골드리스트 시상식 외 5건','미나리'),
('존 데이비드 워싱턴', '제22회 할리우드 필름어워즈','테넷');

insert into DB2021_Actor_Prize(actor, movie, prize)
values 
('곽도원','곡성','제 37회 황금촬영상 시상식 (연기대상)'),
('조여정','기생충','제 40회 청룡영화상 (여우주연상)'),
('김윤석','추격자', '제 29회 청룡영화상 (남우주연상)'),
('여진구','화이:괴물을 삼킨 아이', '제 34회 청룡영화상 (신인남우상)'),
('김혜수','타짜','제 27회 청룡영화상 (여우주연상)'),
('김윤석','타짜','제 44회 대종상 영화제 (남우조연상)'),
('조승우','타짜','제 1회 대한민국 영화연기대상 (남우주연상))'),
('다니엘 래드클리프', '해리포터와 불의 잔', '2016년 49회 시체스영화제 오피셜 판타스틱-남우주연상 수상'),
('엠마 왓슨', '해리포터와 불의 잔', '2017년 26회 MTV 영화&TV 어워즈 영화-최고의 배우상 수상'),
('루퍼트 그린트', '해리포터와 불의 잔', '영국 비평가 협회-최우수 신인상'),
('하정우', '신과 함께-인과 연', '2018년 제 3회 아시아 아티스트 어워즈-베스트 아티스트상'),
('주지훈', '신과 함께-인과 연', '2018년 제 3회 아시아 아티스트 어워즈 올해의 아티스트상'),
('김향기', '신과 함께-인과 연', '2018년 제39회 청룡영화상 여우조연상'),
('키이라 니이틀리', '호두까기 인형과 4개의 왕국', '2003년 23회 런던 비평가 협회상 영국신인상'),
('매켄지 포이', '트와일라잇', '2015년 41회 새턴 어워즈 최우수 신인배우상'),
('크리스틴 스튜어트', '트와일라잇', '2016년 50회 전미 비평가 협회상 여우조연상 수상'),
('이바나 바쿠에로', '판의 미로-오델리아와 세 개의 열쇠', '2007년 제21회 고야어워드'),
('샐리 호킨스', '셰이프 오브 워터: 사랑의 모양', '제 58회 베를린 국제 영화제 은곰상 여우주연상'),
('마이클 섀넌', '셰이프 오브 워터: 사랑의 모양', '2015년 41회 LA 비평가 협회상 남우주연상 수상'),
('에바 그린', '미스 페레그린과 이상한 아이들의 집', '2007년 60회 영국 아카데미 시상식 신인상 수상'),
('조니 뎁', '유령신부', '2016년 31회 산타바바라 국제영화제 말틴 모던 마스터 상 수상');