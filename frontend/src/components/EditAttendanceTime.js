import { useState } from "react";
import { useForm } from "react-hook-form";
import { toast } from 'react-toastify';
import { axiosFetch } from "../../api/fetch";
export const EditAttendanceTime = ({
    attendanceTime
}) => {
    const [loading, setLoading] = useState(false);
    // formNumber 0 then officeStart
    // formNumber 1 then officeEnd
    const [formNumber, setFormNumber] = useState(0);
    const { register, handleSubmit } = useForm();
    const onSubmit = async (data, event) => {
        setLoading(true);
        event.preventDefault();
        const officeStartOrEndKey = formNumber == 0 ? "officeStart" : "officeEnd"
        const officeStartOrEndMessage = formNumber == 0 ? "office start" : "office end"
        try {
            await axiosFetch.patch(`/officeHour`, {
                op: "update",
                key: officeStartOrEndKey,
                value: data[officeStartOrEndKey]
            })
            await setTimeout(() => {
                setLoading(false);
                toast.success(`Successfully updated the ${officeStartOrEndMessage} employee time`, {
                    position: toast.POSITION.TOP_RIGHT
                });
            }, 3000);
        }
        catch (error) {
            setLoading(false);
            toast.error(`${error.response?.status || ""} Error: ${error.response?.error || error.message}`)
        }
    }

    return (
        <div>
            <h1 className='pt-12 pb-5 text-xl font-bold'>
                Employee timing
            </h1>

            <form
                className="text-medium w-3/4"
                onSubmit={handleSubmit(onSubmit)}
            >
                <div className="mx-auto">
                    <div className="grid lg:grid-cols-2">
                        <div>
                            <input
                                id="designation"
                                defaultValue={attendanceTime?.officeStart}
                                {...register("officeStart")}
                                required
                                title="Enter your start time please"
                                className="input input-bordered dark:bg-white inline w-full mb-2"
                            />
                            <button id="officeStart" className={`btn btn-sm btn-primary my-4 ${loading && formNumber==0 ? "loading" : ""}`} type="submit" >
                                Edit Start Time
                            </button>
                        </div>
                        <div>
                        <input
                                id="designation"
                                defaultValue={attendanceTime?.officeEnd}
                                {...register("officeEnd")}
                                required
                                title="Enter your endTime please"
                                className="input input-bordered dark:bg-white inline w-full mb-2"
                            />
                            <button id="officeEnd" className={`btn btn-sm btn-primary mt-4 ${loading && formNumber==1 ? "loading" : ""}`} type="submit" 
                                onClick={()=> {
                                    setFormNumber(1);
                                }}
                            >
                                Edit End Time
                            </button>
                        </div>
                    </div>

                </div>
            </form>
        </div>
    );
}

