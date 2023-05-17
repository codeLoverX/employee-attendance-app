import { useMemo, useState } from 'react';
import { axiosFetch } from '../../api/fetch';
import Layout from '@/components/layout/Layout';
import { AttendanceList } from '@/components/AttendanceList';
import { SearchByNameOrDepartment } from '@/components/SearchByNameOrDepartment';
import { toast } from 'react-toastify';

export default function Attendance({ _attendanceList }) {
    const [attendanceList, setAttendanceList] = useState(_attendanceList);
    const handleQuery = async ({ query, search }) => {
        try {
            if (String(query).trim() ===""){
                toast.error("Please choose whether to search by department or name...")
            }
            const data = (await axiosFetch.get(`employee/attendance/?${query}=${search}`)).data
            console.log({ attendanceList, data })
            await setAttendanceList(data)
        }
        catch (error) {
            toast.error(`${error.response?.status || ""} Error: ${error.response?.error || error.message}`)
        }
    }
    const reloadAll = async () => {
        try {
            await axiosFetch.get(`attendance/CSVAttendanceCreate`)
            const data = (await axiosFetch.get("employee/attendance")).data
            await setAttendanceList(data)
        }
        catch (error) {
            toast.error(`${error.response?.status || ""} Error: ${error.response?.error || error.message}`)
        }
    }
    const deleteAll = async () => {
        try {
            await axiosFetch.delete(`employee/attendance`)
            await setAttendanceList([])
        }
        catch (error) {
            toast.error(`${error.response?.status || ""} Error: ${error.response?.error || error.message}`)
        }
    }
    return useMemo(() => (
        <Layout>
            <main
                className={`min-h-screen font-primary`}
            >
                <div className="px-6 md:px-24 2xl:px-96">
                    <SearchByNameOrDepartment handleQuery={handleQuery} />
                    <AttendanceList
                        attendanceList={attendanceList}
                        deleteAll={deleteAll}
                        reloadAll={reloadAll}
                    />
                </div>
            </main>
        </Layout>
    ))
}

export async function getServerSideProps() {

    const attendanceList = (await axiosFetch.get("employee/attendance")).data

    return {
        props: {
            _attendanceList: attendanceList,
        },

    }
}