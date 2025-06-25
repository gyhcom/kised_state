package state.member.infrastructure.JPA;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import state.member.domain.entity.TabCommStats;

import java.time.LocalDate;
import java.util.List;

public interface JpaTabCommStatsRepository extends JpaRepository<TabCommStats, Long> {
    @Query(value = """
        SELECT seq, d_version, source_system, data_type, stat_type, cnt, base_dt, reg_dt
          FROM (
            SELECT seq, d_version, source_system, data_type, stat_type, cnt, base_dt, reg_dt
              FROM tab_comm_stats
             WHERE stat_type = :statType
             ORDER BY base_dt DESC
             LIMIT 30
          ) A
          ORDER BY base_dt asc
    """, nativeQuery = true)
    List<TabCommStats> findTop30ByStatTypeOrderByBaseDtAsc(@Param("statType") String statType);

    TabCommStats findTopByStatTypeOrderByBaseDtDesc(String statType);

    List<TabCommStats> findByStatTypeAndBaseDtBetweenOrderByBaseDtDesc(String statType, LocalDate startDate, LocalDate endDate);

    @Query(value = """
        SELECT * FROM (
            SELECT seq, d_version, source_system, data_type, stat_type, cnt, base_dt, reg_dt
              FROM tab_comm_stats
             WHERE 1=1
               AND stat_type = :statType
               AND base_dt in (
                    SELECT LAST_DAY(DATE_SUB(SYSDATE(), INTERVAL n MONTH)) AS month_last_day
                      FROM (
                        SELECT 1 as n UNION ALL
                        select 2 UNION ALL
                        select 3 UNION ALL
                        select 4 UNION ALL
                        select 5
                      ) AS months
                      ORDER BY month_last_day
               )
            UNION ALL
                SELECT * FROM (
                SELECT seq, d_version, source_system, data_type, stat_type, cnt, base_dt, reg_dt
                  FROM tab_comm_stats
                 WHERE 1=1
                   AND stat_type = :statType
                 ORDER BY base_dt DESC limit 1
             ) as A
        ) as B
        ORDER BY B.base_dt
    """, nativeQuery = true)
    List<TabCommStats> findByStatTypeMonthly(@Param("statType") String statType);

    @Query(value = """
        SELECT * FROM (
            SELECT seq, d_version, source_system, data_type, stat_type, cnt, base_dt, reg_dt
              FROM tab_comm_stats
             WHERE 1=1
               AND stat_type = :statType
               AND base_dt in (
                    SELECT STR_TO_DATE(CONCAT(YEAR(DATE_SUB(SYSDATE(), INTERVAL n YEAR)), '-12-31'), '%Y-%m-%d') AS year_last_day
                      FROM (
                          SELECT 1 AS n UNION ALL
                          SELECT 2
                      ) AS years
                      ORDER BY year_last_day
               )
            UNION ALL
            select * from (
                SELECT seq, d_version, source_system, data_type, stat_type, cnt, base_dt, reg_dt
                FROM tab_comm_stats
                WHERE 1=1
                  AND stat_type = :statType
                ORDER BY base_dt DESC limit 1
            ) as A
       ) as B
       ORDER BY B.base_dt
    """, nativeQuery = true)
    List<TabCommStats> findByStatTypeYearly(@Param("statType") String statType);
}
