import { useForm } from "react-hook-form";
export const SearchByNameOrDepartment = ({
    handleQuery
}) => {
    const { register, handleSubmit } = useForm();
    const onSubmit = async (data, event) => {
        event.preventDefault();
        handleQuery(data);
    }

    return (
        <div>
            <h1 className='pt-12 pb-5 text-xl font-bold'>
                Search by name or department for attendances...
            </h1>

            <form
                className="text-medium w-3/4"
                onSubmit={handleSubmit(onSubmit)}
            >
                <div className="mx-auto">
                    <div>
                        <select  {...register("query")} className="select select-primary w-2/6 mr-3 inline max-w-xs">
                            <option disabled selected value="">
                                Department or name?
                            </option>
                            <option value="department">Department</option>
                            <option value="name">Name</option>
                        </select>
                        <input id="department"
                            {...register("search")}
                            required
                            title="Enter your search string please"
                            placeholder="Enter your search string please..."
                            className="input input-bordered border-primary dark:bg-white inline w-2/6 mr-3 "
                        />
                        <button className={`inline btn btn-primary relative top-1`} type="submit" >
                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-search"><circle cx="11" cy="11" r="8"></circle><line x1="21" y1="21" x2="16.65" y2="16.65"></line></svg>                            </button>
                    </div>

                </div>
            </form>
        </div>
    );
};

