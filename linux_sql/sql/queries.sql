SELECT cpu_number, id, total_mem
    FROM host_info
    ORDER BY total_mem DESC;

SELECT date_trunc('hour', timestamp) + date_part('minute', timestamp):: int / 5 * interval '5 min'
    FROM host_usage;

CREATE FUNCTION round5(ts timestamp) RETURNS timestamp AS
    $$
    BEGIN
        RETURN date_trunc('hour', ts) + date_part('minute', ts):: int / 5 * interval '5 min';
    END;
    $$
    LANGUAGE PLPGSQL;

SELECT host_usage.host_id, host_info.hostname, round5(host_usage.timestamp) as timestamps, (AVG (host_info.mem_total - host_usage.memory_free)/host_info.mem_total) * 100) as avg_used_mem_percentage
    FROM host_usage inner join host_info ON host_usage.host_id = host_info.id
    GROUP BY host_usage.host_id, host_info.hostname, timestamps
    ORDER BY host_usage.host_id ASC, timestamps ASC;