import axios from "axios";
import { AttendanceTable } from "./AttendanceTable";
import { toast } from 'react-toastify';

export const AttendanceList = ({
    attendanceList, deleteAll, reloadAll
}) => {
    const generatePdf = async () => {
        try {
            const response = await
            axios({
                url: 'http://localhost:8000/api/employee/generatePdf/', 
                method: 'POST',
                responseType: 'blob',
                data: JSON.stringify(attendanceList),
                headers: {
                    "Content-Type": "application/json",
                },
            })
            // console.log({ response })
            const fileURL = window.URL.createObjectURL(new Blob([response.data]));
            let alink = document.createElement('a');
            alink.href = fileURL;
            alink.download = 'AttendancePDF.pdf';
            alink.click();
            alink.remove();
            toast.success("Downloaded your pdf!")

        } catch (error) {
            toast.error(`${error.response?.status || ""} Error: ${error.response?.error || error.message}`)
        }
    }
    return (
        <div className="mt-3 pb-12 text-medium">
            <h3 className='pt-5 pb-3 text-xl font-bold'>
                <span>Attendance List</span>
                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5} stroke="currentColor"
                    className="ml-3 w-6 h-6 inline cursor-pointer text-red-500 hover:text-blue-500"
                    onClick={() => { deleteAll() }}
                >
                    <path strokeLinecap="round" strokeLinejoin="round" d="M14.74 9l-.346 9m-4.788 0L9.26 9m9.968-3.21c.342.052.682.107 1.022.166m-1.022-.165L18.16 19.673a2.25 2.25 0 01-2.244 2.077H8.084a2.25 2.25 0 01-2.244-2.077L4.772 5.79m14.456 0a48.108 48.108 0 00-3.478-.397m-12 .562c.34-.059.68-.114 1.022-.165m0 0a48.11 48.11 0 013.478-.397m7.5 0v-.916c0-1.18-.91-2.164-2.09-2.201a51.964 51.964 0 00-3.32 0c-1.18.037-2.09 1.022-2.09 2.201v.916m7.5 0a48.667 48.667 0 00-7.5 0" />
                </svg>
                <svg xmlns="http://www.w3.org/2000/svg"
                    className="ml-3 w-6 h-6 inline cursor-pointer text-green-500 hover:text-blue-500"
                    onClick={() => { reloadAll() }}
                    width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-refresh-cw"><polyline points="23 4 23 10 17 10"></polyline><polyline points="1 20 1 14 7 14"></polyline><path d="M3.51 9a9 9 0 0 1 14.85-3.36L23 10M1 14l4.64 4.36A9 9 0 0 0 20.49 15"></path></svg>
                {/* <Link > */}
                <svg xmlns="http://www.w3.org/2000/svg"
                    className="ml-3 w-6 h-6 inline cursor-pointer text-cyan-500 hover:text-blue-500"
                    onClick={() => { generatePdf() }}
                    width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-file-text"><path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"></path><polyline points="14 2 14 8 20 8"></polyline><line x1="16" y1="13" x2="8" y2="13"></line><line x1="16" y1="17" x2="8" y2="17"></line><polyline points="10 9 9 9 8 9"></polyline></svg>
                {/* </Link> */}

            </h3>
            <AttendanceTable attendanceList={attendanceList} />
        </div >

    );
};
