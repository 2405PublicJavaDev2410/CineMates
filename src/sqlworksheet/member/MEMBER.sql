-- DROP TABLE MEMBER_TBL;
-- DROP TABLE PROFILE_IMG_TBL;
-- DROP TABLE QNA_TBL;
-- DROP TABLE QNA_FILE_TBL;
-- DROP SEQUENCE SEQ_PROFILE_IMG_NO;
-- DROP SEQUENCE SEQ_QNA_NO;
-- DROP SEQUENCE SEQ_QNA_FILE_NO;

-- 회원 테이블 생성
CREATE TABLE MEMBER_TBL (
    MEMBER_ID VARCHAR2(50) PRIMARY KEY,
    MEMBER_PW VARCHAR2(100) NOT NULL,
    NAME VARCHAR2(30) NOT NULL,
    BIRTH_DATE TIMESTAMP NOT NULL,
    PHONE VARCHAR2(100) NOT NULL,
    EMAIL VARCHAR2(100) NOT NULL UNIQUE,
    PROFILE_IMG VARCHAR2(100),
    STATUS VARCHAR2(10) DEFAULT 'NORMAL',
    DELETE_YN VARCHAR2(3) DEFAULT 'N' NOT NULL,
    ROLE VARCHAR2(30) DEFAULT 'MEMBER' NOT NULL,
    REG_DATE TIMESTAMP DEFAULT SYSTIMESTAMP NOT NULL
);

-- 프로필사진 테이블 생성
CREATE TABLE PROFILE_IMG_TBL (
    PROFILE_IMG_NO NUMBER PRIMARY KEY,
    FILE_NAME VARCHAR2(100) NOT NULL,
    FILE_RENAME VARCHAR2(100) NOT NULL,
    FILE_PATH VARCHAR2(100) NOT NULL,
    MEMBER_ID VARCHAR2(50) NOT NULL
);

-- 문의 테이블 생성
CREATE TABLE QNA_TBL (
    QNA_NO NUMBER PRIMARY KEY,
    TITLE VARCHAR2(300),
    CONTENT CLOB NOT NULL,
    MEMBER_ID VARCHAR2(50) NOT NULL,
    REG_DATE TIMESTAMP DEFAULT SYSTIMESTAMP NOT NULL,
    MOD_DATE TIMESTAMP DEFAULT SYSTIMESTAMP NOT NULL,
    PARENT_QNA_NO NUMBER,
    CONSTRAINT FK_QNA_PARENT FOREIGN KEY (PARENT_QNA_NO) REFERENCES QNA_TBL(QNA_NO)
);

-- 문의 첨부파일 테이블 생성
CREATE TABLE QNA_FILE_TBL (
    QNA_FILE_NO NUMBER PRIMARY KEY,
    FILE_NAME VARCHAR2(100) NOT NULL,
    FILE_RENAME VARCHAR2(100) NOT NULL,
    FILE_PATH VARCHAR2(100) NOT NULL,
    QNA_NO NUMBER NOT NULL
);

-- 프로필사진 시퀀스 생성
CREATE SEQUENCE SEQ_PROFILE_IMG_NO
    NOCACHE
NOCYCLE;

-- 문의 시퀀스 생성
CREATE SEQUENCE SEQ_QNA_NO
    NOCACHE
NOCYCLE;

-- 문의 첨부파일 시퀀스 생성
CREATE SEQUENCE SEQ_QNA_FILE_NO
    NOCACHE
NOCYCLE;

-- 회원 정보 삭제 시 프로필사진 데이터 함께 삭제
ALTER TABLE PROFILE_IMG_TBL
    ADD CONSTRAINT FK_PROFILE_IMG_MEMBER
        FOREIGN KEY (MEMBER_ID)
            REFERENCES MEMBER_TBL(MEMBER_ID)
            ON DELETE CASCADE;

-- 회원 정보 삭제 시 문의 데이터 함께 삭제
ALTER TABLE QNA_TBL
    ADD CONSTRAINT FK_QNA_MEMBER
        FOREIGN KEY (MEMBER_ID)
            REFERENCES MEMBER_TBL(MEMBER_ID)
            ON DELETE CASCADE;

-- 회원 정보 삭제 시 문의파일 데이터 함께 삭제
ALTER TABLE QNA_FILE_TBL
    ADD CONSTRAINT FK_QNA_FILE_QNA
        FOREIGN KEY (QNA_NO)
            REFERENCES QNA_TBL(QNA_NO)
            ON DELETE CASCADE;

-- 운영자 계정 생성 쿼리문
INSERT INTO MEMBER_TBL(MEMBER_ID, MEMBER_PW, NAME, BIRTH_DATE, PHONE, EMAIL, STATUS, ROLE, REG_DATE)
VALUES('admin', 'admin12#$', '운영자', SYSTIMESTAMP, '01012345678', 'admin@admin.com', DEFAULT, 'ADMIN', SYSTIMESTAMP);

-- 회원 더미데이터 생성
BEGIN
FOR i IN 1..100 LOOP
        INSERT INTO MEMBER_TBL (
            MEMBER_ID,
            MEMBER_PW,
            NAME,
            BIRTH_DATE,
            PHONE,
            EMAIL,
            PROFILE_IMG,
            STATUS,
            DELETE_YN,
            ROLE,
            REG_DATE
        )
        VALUES (
            'MEM' || LPAD(i, 3, '0'),
           'MEM' || LPAD(i, 3, '0')|| '!#',
            '멤버'||i,
            TO_DATE('1990-01-01', 'YYYY-MM-DD') + DBMS_RANDOM.VALUE(0, 10000),
            '010' || LPAD(ROUND(DBMS_RANDOM.VALUE(1000, 9999)), 4, '0') || LPAD(ROUND(DBMS_RANDOM.VALUE(1000, 9999)), 4, '0'),
            'mem' || LPAD(i, 3, '0') || '@cinemates.com',
            NULL,
            'NORMAL',
            'N',
            'MEMBER',
            SYSTIMESTAMP
        );
END LOOP;
COMMIT;
END;
/

DELETE FROM MEMBER_TBL;
DELETE FROM QNA_TBL;

-- 문의 더미데이터 생성
BEGIN
FOR i IN 1..100 LOOP
        INSERT INTO QNA_TBL (
            QNA_NO,
            TITLE,
            CONTENT,
            MEMBER_ID,
            REG_DATE,
            MOD_DATE,
            PARENT_QNA_NO
        )
        VALUES (
            i,  -- QNA_NO (순차적)
            'SAMPLE QNA TITLE ' || i,
            'SAMPLE QNA CONTENT ' || i,
            'MEM' || LPAD(i, 3, '0'),
            SYSTIMESTAMP - DBMS_RANDOM.VALUE(0, 365),
            SYSTIMESTAMP - DBMS_RANDOM.VALUE(0, 365),
            NULL
        );
END LOOP;
COMMIT;
END;
/

COMMIT;