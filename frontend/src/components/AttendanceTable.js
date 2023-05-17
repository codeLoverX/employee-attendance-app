export const AttendanceTable = ({
    attendanceList
}) => {
    return (<>
        <div class="mt-3">
            <table class="table table-zebra w-full">
                <thead>
                    <tr>
                        <th></th>
                        <th>Employee Name</th>
                        <th>Employee Department</th>
                        <th>Employee Designation</th>
                        <th>Office In</th>
                        <th>Office Out</th>
                        <th>Date</th>

                    </tr>
                </thead>
                <tbody>
                    {attendanceList.map((value, index) => (
                        <tr key={value.id}>
                            <th>{index + 1}</th>
                            <td>{value.employee.name}</td>
                            <td>{value.employee.designation}</td>
                            <td>{value.employee.department}</td>
                            <td className={`${value.lateOfficeIn ? "text-red-600" : ""}`}>{value.officeIn}</td>
                            <td className={`${value.earlyOfficeOut ? "text-yellow-600" : ""}`}>{value.officeOut}</td>
                            <td>{value.date}</td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    </>)
}