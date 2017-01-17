/*
- we want the department name and the number of employees in the department. 
- LEFT JOIN employee where the department dept id = employee dept_id, only gets employees in that department, yet keeps departments with 0 employees (as it preserves data from Department table)
- Group by Department name as those are the groups we're forming
- Order by the number of employees in the department first (ORDER BY COUNT(e.ID))
- Then if two match, order by the department name alphabetically*/
SELECT d.NAME as "Department Name", COUNT(e.ID) as "Number of Employees" FROM Department d LEFT JOIN Employee as e on d.DEPT_ID=e.DEPT_ID GROUP BY d.NAME ORDER BY COUNT(e.ID) DESC, d.NAME;