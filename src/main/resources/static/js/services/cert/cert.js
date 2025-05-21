let currentMemberData;
let annualMemberData;
let monthlyMemberData;

let annualLoginData;
let monthlyLoginData;
let dailyLoginData;

let dailyListData;

document.addEventListener("DOMContentLoaded", function () {
    datePickerInit();
    init();

    // 로고/타이틀
    gsap.from("#systemNm", {
        duration: 1,
        y: -50,
        opacity: 0,
        ease: "power3.out"
    });

    // 상태 아이콘 + 텍스트
    gsap.from(["#statusIcon", "#statusText"], {
        duration: 1,
        x: -20,
        opacity: 0,
        delay: 0.3,
        stagger: 0.2,
        ease: "power2.out"
    });

    // 날짜 선택 영역 & 버튼
    gsap.from(".date-picker", {
        duration: 1,
        y: -50,
        opacity: 0,
        ease: "power3.out",
        onComplete: () => {
            document.querySelector('.date-picker').style.transform = 'none';
        }
    });
})

function init() {

    $.ajax({
        url: '/cert/certCnt',
        type: 'GET',
        dataType: 'json',
        success: function(data) {
            setApiSuccessIcon();

            //데이터가 존재하지 않을 시 API 호출 상태 ICON 업데이트
            // if(!data) {
            //     setApiFailureIcon();
            // }

            dailyListData = data.certDailyList;

            setCertCnt(data.certDailyCnt.stats);
            createCertDailyChart();
        },
        error: function(error) { // 에러 시 실행
            console.error('Error:', error);
            // 실패 여부를 H1 태그에 표현하기
            setApiFailureIcon();
        }
    });
}

function createCertDailyChart() {
    {
        const el = document.getElementById('certCntDailyChart');
        let data = {
            // TODO 데이터 쌓이면 categories도 for문 안에서 세팅될 수 있도록 수정되어야 함
            categories: [
                '2025-04-17',
                '2025-04-18',
                '2025-04-19',
                '2025-04-20',
                '2025-04-21'
            ],
            series: [
                {
                    name: '확인서 신청 건수',
                    data: [],
                },
                {
                    name: '확인서 발급 건수',
                    data: [],
                },
                {
                    name: '확인서 반려 건수',
                    data: [],
                }
            ],
        };

        // data 값은 Number 타입으로 세팅되어야 함. String으로 세팅돼서 한참을 헤맴
        for( var i = 0 ; i < dailyListData.length ; i++ ) {
            data.series[0].data.push(Number(dailyListData[i].confmDocAplyCnt));
            data.series[1].data.push(Number(dailyListData[i].confmDocIssuCnt));
            data.series[2].data.push(Number(dailyListData[i].confmDocRjctCnt));
        }

        const theme = {
            series: {
                dataLabels: {
                    fontFamily: 'monaco',
                    fontSize: 10,
                    fontWeight: 300,
                    useSeriesColor: true,
                    textBubble: {
                        visible: true,
                        paddingY: 3,
                        paddingX: 6,
                        arrow: {
                            visible: true,
                            width: 5,
                            height: 5,
                            direction: 'bottom',
                        },
                    },
                },
            },
        };

        const options = {
            chart: { title: '확인서 건수 Chart', width: 'auto', height: 550 },
            xAxis: {
                title: 'day',
            },
            yAxis: {
                title: 'Count',
            },
            tooltip: {
                formatter: (value) => `${value}건`,
            },
            legend: {
                align: 'bottom',
            },
            series: {
                dataLabels: {
                    visible: true,
                    offsetY: -10
                },
            },
            theme,
        };

        toastui.Chart.lineChart({ el, data, options });
    }
}


function getTheme() {
    return {
        series: {
            dataLabels: {
                fontFamily: 'Arial',
                fontSize: 12,
                fontWeight: 400,
                color: '#dc3545',
                textBubble: {visible: true, arrow: {visible: true}},
            },
        },
    };
}

function validateData() {
    if(!currentMemberData || currentMemberData.length <= 0) return false;
    if(!annualMemberData || annualMemberData.length <= 0) return false;
    if(!monthlyMemberData || monthlyMemberData.length <= 0) return false;

    if(!annualLoginData || annualLoginData.length <= 0) return false;
    if(!monthlyLoginData || monthlyLoginData.length <= 0) return false;
    if(!dailyLoginData || dailyLoginData.length <= 0) return false;

    return true;
}

function datePickerInit() {
    rangeDatePickerInit()
}

function setCertCnt(obj) {
    if(!obj) {
        console.error('창업기업확인 DATA IS NULL');
        return;
    }

    // 통계 카드 애니메이션
    gsap.from(".card-group", {
        duration: 1,
        y: 50,
        opacity: 0,
        stagger: 0.2,
        delay: 1,
        ease: "back.out(1.4)"
    });

    // 차트 카드 애니메이션
    gsap.from("#contentDiv", {
        duration: 1,
        scale: 0.9,
        opacity: 0,
        delay: 1.5,
        ease: "power2.out"
    });

    /* 확인서 신청 건수 */
    gsap.to("#applyCnt", {
        innerText: obj.confmDocAplyCnt,
        duration: 3,
        snap: "innerText",
        onUpdate: function () {
            document.querySelector("#applyCnt").innerText =
                Math.floor(this.targets()[0].innerText).toLocaleString();
        }
    });

    /* 확인서 발급 건수 */
    gsap.to("#issueCnt", {
        innerText: obj.confmDocIssuCnt,
        duration: 3,
        snap: "innerText",
        onUpdate: function () {
            document.querySelector("#issueCnt").innerText =
                Math.floor(this.targets()[0].innerText).toLocaleString();
        }
    });

    /* 확인서 반려 건수 */
    gsap.to("#rejectCnt", {
        innerText: obj.confmDocRjctCnt,
        duration: 3,
        snap: "innerText",
        onUpdate: function () {
            document.querySelector("#rejectCnt").innerText =
                Math.floor(this.targets()[0].innerText).toLocaleString();
        }
    });

    const date = new Date();
    const year = date.getFullYear();
    let month = (date.getMonth()+1)+"";
    let day = date.getDate()+"";

    // 날짜가 한 자리수 일 경우 "01", "02"... 로 표현하기 위함
    if( day.length === 1 ) {
        day = "0"+day;
    }
    if( month.length === 1 ) {
        month = "0"+month;
    }

    $('#applyYmd').text('(' + year + '-' + month + '-' + day + ' 기준)');
    $('#issueYmd').text('(' + year + '-' + month + '-' + day + ' 기준)');
    $('#rejectYmd').text('(' + year + '-' + month + '-' + day + ' 기준)');
}