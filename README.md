# 📚 독서 기록 서비스
 책을 검색하여 독서 중인 책, 독서 계획과 독서량에 대해 기록할 수 있고, 서비스 사용자들과 소통할 수 있는 서비스 입니다.

# 프로그램 기능 및 설계

<회원가입 및 로그인 기능>
  - 이메일과 비밀번호를 통해 가입할 수 있으며, 회원가입 후 닉네임을 설정할 수 있다.
  - 사용자는 회원가입 시 입력한 이메일과 비밀번호를 통해 로그인할 수 있다.

<책 검색과 책 정보 조회 기능>
  - 책 검색은 책 제목 검색, 직접 입력, 바코드 검색을 통해 할 수 있다.
  - 책 정보는 책 제목, 저자, 출판사, ISBN, 전체페이지 수를 보여준다.
  - 책 검색으로 조회한 책도 내 서재에 등록할 수 있다.

<책 등록 기능>
  - 읽고 싶은 책, 읽고 있는 책, 읽은 책으로 구분해 등록할 수 있다.
  - 읽고 있는 책은 시작날짜와 종료날짜, 진행상황을 등록할 수 있다.
  - 읽은 책은 점수와 리뷰를 등록할 수 있다.

<독서량과 목표 설정 기능>
  - 독서량은 일간, 월간, 연간으로 나누어 독서 시간, 페이지, 권수로 기록할 수 있다.
  - 목표는 일간, 월간 -> 페이지, 권수를 등록 / 연간 -> 권수로 등록할 수 있다.
  - 목표는 독서량과 비교하여 몇 퍼센트 진행되었는지 알려준다.

<독서 노트, 감상문 작성 및 조회 기능>
  - 독서 노트는 맘에 드는 구문(텍스트)만으로 작성할 수 있고, 조회할 시 작성 내용, 닉네임, 작성일시를 보여준다.
  - 감상문은 읽은 책을 설정 후 감상문 제목과 내용(모두 텍스트)을 작성할 수 있으며, 조회할 시 감상문 제목, 감상문 내용, 닉네임, 작성일시를 보여준다.

<댓글 작성 및 조회 기능>
  - 사용자는 독서 노트와 감상문에 댓글(텍스트)을 작성할 수 있다.
  - 작성된 독서 노트, 감상문을 조회시 댓글도 함께 조회되며 최신순으로 정렬된다.
  - 작성된 댓글은 댓글 내용, 닉네임, 작성 일시를 보여준다.

# ERD
<a href='https://ifh.cc/v-AgG9G1' target='_blank'><img src='https://ifh.cc/g/AgG9G1.jpg' border='0'></a>

#
* 가능하다면 추가로 타이머 기능도 구현해보고 싶습니다.
