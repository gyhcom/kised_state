let pbancRegChartData;
let instBizPbancChartData;
let intgPbancRegCnt;
let bizPbancRegCnt;
let latestDataCnt;

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
});

function init() {
    $.ajax({
        url: '/kstup/instBizPbancRegCnt',
        type: 'GET',
        dataType: 'json',
        success: function(data) {
            console.dir(data);
            setApiSuccessIcon();

            //데이터가 존재하지 않을 시 API 호출 상태 ICON 업데이트
            if(!data) {
                setApiFailureIcon();
            }

            pbancRegChartData = data.dailyCntList;
            instBizPbancChartData = data.instPbancRegCntMap;
            latestDataCnt = data.latestCnt;
            // intgPbancRegCnt = data.intgPbancRegCnt;
            // bizPbancRegCnt = data.bizPbancRegCnt;

            createPbancRegChart();
            createInstBizPbancChart();
            setInstRegCnt();
        },
        error: function(error) {
            console.error('Error:', error);
            setApiFailureIcon();
        }
    });
}

function createPbancRegChart() {
    {
        const el = document.getElementById('pbancRegChart');
        const data = {
            categories: [],
            series: [
                {
                    name: '통합공고 등록 건수',
                    data: [],
                },
                {
                    name: '사업공고 등록 기관 수',
                    data: [],
                }
            ],
        };

        if( pbancRegChartData != null ) {
            for( var i = 0 ; i < pbancRegChartData.length ; i++ ) {
                data.categories.push(pbancRegChartData[i].baseDt2);
                data.series[0].data.push(Number(pbancRegChartData[i].bizPbancRegInstCnt));
                data.series[1].data.push(Number(pbancRegChartData[i].intgPbancRegCnt));
            }
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
            chart: { title: '공고등록 관련 Chart', width: 'auto', height: 550 },
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

function createInstBizPbancChart() {
    {
        const el = document.getElementById('instBizPbancChart');
        const data = {
            categories: [],
            series: [
                {
                    name: '민간',
                    data: [],
                },
                {
                    name: '공공기관',
                    data: [],
                },
                {
                    name: '지자체',
                    data: [],
                },
                {
                    name: '교육기관',
                    data: [],
                }
            ],
        };

        // 기관유형 : 민간 데이터 세팅
        if( instBizPbancChartData.private != null ) {
            // 민간, 공공기관, 지자체, 교육기관 모두 데이터 length가 같다는 가정
            for( var i = 0 ; i < instBizPbancChartData.private.length ; i++ ) {
                data.categories.push(instBizPbancChartData.private[i].baseDt2);
                data.series[0].data.push(Number(instBizPbancChartData.private[i].bizPbancRegCnt));
                data.series[1].data.push(Number(instBizPbancChartData.public[i].bizPbancRegCnt));
                data.series[2].data.push(Number(instBizPbancChartData.local[i].bizPbancRegCnt));
                data.series[3].data.push(Number(instBizPbancChartData.edu[i].bizPbancRegCnt));
            }
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
            chart: { title: '기관유형별 사업공고 등록 Chart', width: 'auto', height: 550 },
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

function datePickerInit() {
    rangeDatePickerInit();
}

function setInstRegCnt() {
    // 통계 카드 애니메이션
    gsap.from(".card-group", {
        duration: 1,
        y: 50,
        opacity: 0,
        stagger: 0.2,
        delay: 1,
        ease: "back.out(1.4)"
    });

    /* 통합공고 등록 건수 */
    gsap.to("#intgPbancRegCnt", {
        innerText: latestDataCnt.intgPbancRegCnt,
        duration: 3,
        snap: "innerText",
        onUpdate: function () {
            document.querySelector("#intgPbancRegCnt").innerText =
                Math.floor(this.targets()[0].innerText).toLocaleString();
        }
    });

    /* 사업공고 등록 기관(주관기관) 수 */
    gsap.to("#bizPbancRegInst", {
        innerText: latestDataCnt.bizPbancRegInstCnt,
        duration: 3,
        snap: "innerText",
        onUpdate: function () {
            document.querySelector("#bizPbancRegInst").innerText =
                Math.floor(this.targets()[0].innerText).toLocaleString();
        }
    });

    // 차트 카드 애니메이션
    gsap.from("#contentDiv", {
        duration: 1,
        scale: 0.9,
        opacity: 0,
        delay: 1.5,
        ease: "power2.out"
    });

    $('#intgPbancRegCntYmd').text('(' + latestDataCnt.baseDt2 + ' 기준)');
    $('#bizPbancRegInstYmd').text('(' + latestDataCnt.baseDt2 + ' 기준)');
}