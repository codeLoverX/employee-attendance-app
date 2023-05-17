import { AddEditEmployeeForm } from '@/components/AddEditEmployeeForm';
import { EmployeeList } from '@/components/EmployeeList';
import { useCallback, useState } from 'react';
import { axiosFetch } from '../../api/fetch';
import Layout from '@/components/layout/Layout';
import { EditAttendanceTime } from '@/components/EditAttendanceTime';

export default function Home({ _employeeList, attendanceTime }) {
    const [employeeList, setEmployeeList] = useState(_employeeList);
    const [currentEmployeeIndex, setCurrentEmployeeIndex] = useState(-1);
    const mode = currentEmployeeIndex === -1 ? "ADD" : "EDIT";
    const handleCurrentEmployeeIndex = (index) => {
        setCurrentEmployeeIndex(index);
    }
    const handleCurrentEmployee = useCallback(() => {
        if (currentEmployeeIndex < 0) return { id: 0, title: "", description: "", date: "" }
        else return employeeList[currentEmployeeIndex]
    }, [currentEmployeeIndex])
    const addEmployee = (employee) => {
        setEmployeeList((prev) => [...prev, employee])
    }
    const editEmployee = (employee) => {
        setEmployeeList(employeeList.map((element) =>
            element.id === employee.id ? employee : element
        ))
    }
    const removeEmployee = (id) => {
        setEmployeeList(employeeList.filter((element) =>
            element.id !== id
        ))
    }
    return (
        <Layout>
            <main
                className={`min-h-screen font-primary`}
            >
                <div className="px-6 md:px-24 2xl:px-96">
                    <EditAttendanceTime attendanceTime={attendanceTime}/>
                    <AddEditEmployeeForm currentEmployee={{ ...handleCurrentEmployee() }}
                        mode={mode}
                        handleCurrentEmployeeIndex={handleCurrentEmployeeIndex}
                        addEmployee={addEmployee}
                        editEmployee={editEmployee}
                    />
                    <EmployeeList
                        employeeList={employeeList}
                        handleCurrentEmployeeIndex={handleCurrentEmployeeIndex}
                        removeEmployee={removeEmployee}
                        editEmployee={editEmployee}
                    />
                </div>
            </main>
        </Layout>
    )
}

export async function getServerSideProps() {
   
    const employeeList = (await axiosFetch.get("/employee", {
    })).data
    const attendanceTime = (await axiosFetch.get("/officeHour", {
    })).data
    return {
        props: {
            _employeeList: employeeList,
            attendanceTime
        },

    }
}